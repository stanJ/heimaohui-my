/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.xa3ti.blackcat.base.model.BaseExampleModel;
import com.xa3ti.blackcat.base.util.Pageable;

//只拦截select部分  
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class PaginationInterceptor implements Interceptor {

	// private final static Log log = LogFactory.getLog(PaginationPlugin.class);

	// 存储所有语句名称
	HashMap<String, String> map_statement = new HashMap<String, String>();
	// 用户提供分页计算条数后缀
	static final String COUNT_ID = "_count";

	/**
	 * 获取所有statement语句的名称
	 * <p>
	 *
	 * @param configuration
	 */
	protected synchronized void initStatementMap(Configuration configuration) {
		if (!map_statement.isEmpty()) {
			return;
		}
		Collection<String> statements = configuration.getMappedStatementNames();
		for (Iterator<String> iter = statements.iterator(); iter.hasNext();) {
			String element = iter.next();
			map_statement.put(element, element);
		}
	}

	/**
	 * 获取数据库连接
	 * <p>
	 *
	 * @param transaction
	 * @param statementLog
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection(Transaction transaction, Log statementLog)
			throws SQLException {
		Connection connection = transaction.getConnection();
		if (statementLog.isDebugEnabled()) {
			return ConnectionLogger.newInstance(connection, statementLog);
		} else {
			return connection;
		}
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object parameter = invocation.getArgs()[1];
		Pageable page = seekPage(parameter);
		if (page == null) {
			return invocation.proceed();
		} else {
			return handlePaging(invocation, parameter, page);
		}

	}

	/**
	 * 处理分页的情况
	 * <p>
	 * 
	 * @param invocation
	 * @param parameter
	 * @param page
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	protected Object handlePaging(Invocation invocation, Object parameter,
			Pageable page) throws Exception {
		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		Configuration configuration = mappedStatement.getConfiguration();
		if (map_statement.isEmpty()) {
			initStatementMap(configuration);
		}

		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		// 查询结果集
		StaticSqlSource sqlsource = new StaticSqlSource(configuration,
				getLimitString(boundSql.getSql(), page),
				boundSql.getParameterMappings());

		MappedStatement.Builder builder = new MappedStatement.Builder(
				configuration, "id_temp_result", sqlsource,
				SqlCommandType.SELECT);
		builder.resultMaps(mappedStatement.getResultMaps())
				.resultSetType(mappedStatement.getResultSetType())
				.statementType(mappedStatement.getStatementType());
		MappedStatement query_statement = builder.build();

		// //////////////

		Executor exe = (Executor) invocation.getTarget();
		Connection connection = getConnection(exe.getTransaction(),
				mappedStatement.getStatementLog());
		PreparedStatement ps = connection.prepareStatement(boundSql.getSql());
		setParameters(ps, mappedStatement, boundSql,
				boundSql.getParameterObject());


		Object[] args = invocation.getArgs();

		StatementHandler handler = configuration.newStatementHandler(exe,
				mappedStatement, parameter, (RowBounds) args[2],
				(ResultHandler) args[3], boundSql);
		List data = (List) handler.query(ps, (ResultHandler) args[3]);

		// List data = (List) exeQuery(invocation, query_statement);
		// 设置到page对象

		// page.setRecords(data);
		// page.setCount(getTotalSize(invocation, configuration,
		// mappedStatement, boundSql, parameter));

		page.setModelList(data);
		try {
			page.convertToEntityList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setTotalSize(getTotalSize(invocation, configuration,
				mappedStatement, boundSql, parameter));
		// Page p=new PageImpl(data,page,getTotalSize(invocation, configuration,
		// mappedStatement, boundSql, parameter));

		return data;
	}

	/**
	 * 根据提供的语句执行查询操作
	 * <p>
	 *
	 * @param invocation
	 * @param query_statement
	 * @return
	 * @throws Exception
	 */
	protected Object exeQuery(Invocation invocation,
			MappedStatement query_statement) throws Exception {
		Object[] args = invocation.getArgs();
		return invocation.getMethod().invoke(invocation.getTarget(),
				new Object[] { query_statement, args[1], args[2], args[3] });
	}

	/**
	 * 获取总记录数量
	 * <p>
	 *
	 * @param configuration
	 * @param mappedStatement
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	protected int getTotalSize(Invocation invocation,
			Configuration configuration, MappedStatement mappedStatement,
			BoundSql boundSql, Object parameter) throws Exception {

		String count_id = mappedStatement.getId() + COUNT_ID;
		int totalSize = 0;
		if (map_statement.containsKey(count_id)) {
			// 优先查找能统计条数的sql
			List data = (List) exeQuery(invocation, mappedStatement
					.getConfiguration().getMappedStatement(count_id));
			if (data.size() > 0) {
				totalSize = Integer.parseInt(data.get(0).toString());
			}
		} else {
			Executor exe = (Executor) invocation.getTarget();
			Connection connection = getConnection(exe.getTransaction(),
					mappedStatement.getStatementLog());
			String countSql = getCountSql(boundSql.getSql());
			totalSize = getTotalSize(configuration, mappedStatement, boundSql,
					countSql, connection, parameter);
		}

		return totalSize;
	}

	/**
	 * 拼接查询sql,加入limit
	 * <p>
	 *
	 * @param sql
	 * @param page
	 */
	protected String getLimitString(String sql, Pageable page) {
		StringBuffer sb = new StringBuffer(sql.length() + 100);
		sb.append(sql);
		sb.append(" limit ").append(page.getOffset()).append(",")
				.append(page.getPageSize());
		return sb.toString();
	}

	/**
	 * 拼接获取条数的sql语句
	 * <p>
	 *
	 * @param sqlPrimary
	 */
	protected String getCountSql(String sqlPrimary) {
		String sqlUse = sqlPrimary.replaceAll("[\\s]+", " ");
		String upperString = sqlUse.toUpperCase();
		int order_by = upperString.lastIndexOf(" ORDER BY ");
		if (order_by > -1) {
			sqlUse = sqlUse.substring(0, order_by);
		}
		String[] paramsAndMethod = sqlUse.split("\\s");
		int count = 0;
		int index = 0;
		for (int i = 0; i < paramsAndMethod.length; i++) {
			String upper = paramsAndMethod[i].toUpperCase();
			if (upper.length() == 0) {
				continue;
			}
			if (upper.contains("SELECT")) {
				count++;
			} else if (upper.contains("FROM")) {
				count--;
			}
			if (count == 0) {
				index = i;
				break;
			}
		}
		StringBuilder return_sql = new StringBuilder("SELECT COUNT(1) AS cnt ");
		StringBuilder common_count = new StringBuilder();
		for (int j = index; j < paramsAndMethod.length; j++) {
			common_count.append(" ");
			common_count.append(paramsAndMethod[j]);
		}
		if (upperString.contains(" GROUP BY ")) {
			throw new RuntimeException(
					"不支持group by 分页,请自行提供sql语句以 查询语句+_count结尾.");
		}
		return return_sql.append(common_count).toString();
	}

	/**
	 * 计算总条数
	 * <p>
	 *
	 * @param parameterObj
	 * @param countSql
	 * @param connection
	 * @return
	 */
	protected int getTotalSize(Configuration configuration,
			MappedStatement mappedStatement, BoundSql boundSql,
			String countSql, Connection connection, Object parameter)
			throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalSize = 0;
		try {
			ParameterHandler handler = configuration.newParameterHandler(
					mappedStatement, parameter, boundSql);
			stmt = connection.prepareStatement(countSql);
			handler.setParameters(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalSize = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		}
		return totalSize;
	}

	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters")
				.object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null
					: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName
							.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value)
									.getValue(
											propertyName.substring(prop
													.getName().length()));
						}

						boundSql.setAdditionalParameter(prop.getName(), value);
					} else {
						value = metaObject == null ? null : metaObject
								.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value,
							parameterMapping.getJdbcType());
				}
			}
		}
	}

	/**
	 * 寻找page对象
	 * <p>
	 *
	 * @param parameter
	 */
	@SuppressWarnings("rawtypes")
	protected Pageable seekPage(Object parameter) {
		Pageable pageable = null;
		if (parameter == null) {
			return null;
		}
		if (parameter instanceof Pageable) {
			pageable = (Pageable) parameter;
		} else if (parameter instanceof Map) {
			Map map = (Map) parameter;
			for (Object arg : map.values()) {
				if (arg instanceof Pageable) {
					pageable = (Pageable) arg;
				}
			}
		} else if (parameter instanceof BaseExampleModel) {
			BaseExampleModel baseExampleModel = (BaseExampleModel) parameter;
			pageable = baseExampleModel.getPageable();

		}

		return pageable;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
