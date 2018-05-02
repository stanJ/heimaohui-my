package com.xa3ti.blackcat.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.base.util.QuerySqlExecutor;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.xa3ti.blackcat.business.entity.Log;
import com.xa3ti.blackcat.business.repository.LogRepository;
import com.xa3ti.blackcat.business.service.LogService;
import com.xa3ti.blackcat.event.Event;
import com.xa3ti.blackcat.event.LoginEvent;
import com.xa3ti.blackcat.event.ServiceEvent;


@Service("LogService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class LogServiceImpl extends BaseService implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public XaResult<Page<Log>> findLogNEStatusByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				Log.class.getName(), QueryDao.class);
		Page<Log> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE,
					status));
			page = logRepository.findAll(DynamicSpecifications.bySearchFilter(
					filters.values(), Log.class), pageable);

		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status!=" + status
						+ ")";
				page = QuerySqlExecutor.find(null, Log.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE,
						status));

				page = QuerySqlExecutor.find(filters.values(), Log.class,
						pageable);
			}
		} else if (an.method().equals(BaseConstant.DAO.MYBATIS)) {
			//
		}

		XaResult<Page<Log>> xr = new XaResult<Page<Log>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Log>> findLogEQStatusByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				Log.class.getName(), QueryDao.class);
		Page<Log> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ,
					status));
			page = logRepository.findAll(DynamicSpecifications.bySearchFilter(
					filters.values(), Log.class), pageable);

		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status
						+ ")";
				page = QuerySqlExecutor.find(null, Log.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE,
						status));

				page = QuerySqlExecutor.find(filters.values(), Log.class,
						pageable);
			}
		} else if (an.method().equals(BaseConstant.DAO.MYBATIS)) {
			//
		}

		XaResult<Page<Log>> xr = new XaResult<Page<Log>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Log>> findLogVoByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		QueryDao an = (QueryDao) AnnotationUtil.getAnnotationPresentForEntity(
				Log.class.getName(), QueryDao.class);
		Page<Log> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)) {

			Map<String, SearchFilter> filters = SearchFilter
					.parse(filterParams);
			if (status == null) {// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ,
					status));
			page = logRepository.findAll(DynamicSpecifications.bySearchFilter(
					filters.values(), Log.class), pageable);

		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)) {
			String sqlFilter = (String) filterParams
					.get(BaseConstant.SQLFILTERKEY);
			if (!StringUtils.isBlank(sqlFilter)) { // 混合查
				sqlFilter = sqlFilter.substring(1);
				sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 1);// 去除{}
				sqlFilter = "(" + sqlFilter + ")" + " and (status==" + status
						+ ")";
				page = QuerySqlExecutor.find(null, Log.class, pageable,
						sqlFilter);
			} else {// 传统and查
				Map<String, SearchFilter> filters = SearchFilter
						.parse(filterParams);
				if (status == null) {// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE,
						status));

				page = QuerySqlExecutor.find(filters.values(), Log.class,
						pageable);
			}
		} else if (an.method().equals(BaseConstant.DAO.MYBATIS)) {
			//
		}

		XaResult<List<Log>> xr = new XaResult<List<Log>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	public XaResult<Log> createLog(String userId, Log model)
			throws BusinessException {

		XaResult<Log> xr = new XaResult<Log>();
		// 此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if (userId != null) {
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		Log obj = logRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	public XaResult<Log> updateLog(String userId, Log model)
			throws BusinessException {
		Log obj = logRepository.findOne(model.getTid());
		XaResult<Log> xr = new XaResult<Log>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setClient(model.getClient());
			obj.setUserId(model.getUserId());
			obj.setLdate(model.getLdate());
			obj.setService(model.getService());
			obj.setMethod(model.getMethod());
			obj.setUsedTimes(model.getUsedTimes());
			obj.setResult(model.getResult());
			// 此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = logRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	public XaResult<Log> operateLog(String userId, Long modelId, Integer status)
			throws BusinessException {
		if (status == null) {
			status = XaConstant.Status.delete;
		}
		Log obj = logRepository.findByTidAndStatusNot(modelId, status);
		XaResult<Log> xr = new XaResult<Log>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = logRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Log> findLog(Long modelId) throws BusinessException {
		Log obj = logRepository.findByTidAndStatusNot(modelId,
				XaConstant.Status.delete);
		XaResult<Log> xr = new XaResult<Log>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Log> multiOperateLog(String userId, String modelIds,
			Integer status) throws BusinessException {
		XaResult<Log> xr = new XaResult<Log>();
		if (status == null) {
			status = XaConstant.Status.delete;
		}
		if (modelIds != null) {
			if (StringUtils.indexOf(modelIds, ",") > 0) {
				String[] ids = modelIds.split(",");
				for (String id : ids) {
					Log obj = logRepository.findByTidAndStatusNot(
							Long.parseLong(id), status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = logRepository.save(obj);
					} else {
						throw new BusinessException(
								XaConstant.Message.object_not_find);
					}
				}
			} else {
				Log obj = logRepository.findByTidAndStatusNot(
						Long.parseLong(modelIds), status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = logRepository.save(obj);
				} else {
					throw new BusinessException(
							XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xa3ti.blackcat.business.service.LogService#logEvent(com.xa3ti.blackcat
	 * .proxy.invoke.event.Event)
	 */
	@Override
	public void logEvent(Event event) throws BusinessException {
		if (event instanceof ServiceEvent) {
			ServiceEvent se = (ServiceEvent) event;

			Log log = new Log();

			log.setClient(se.getSource());
			log.setLdate(new Date());

			Token t = (Token) se.getInvocation().getAttachment(
					BaseConstant.ATTACTMENT_KEY_TOKEN);
			if (t != null)
				log.setUserId(t.getUserId());

			// log.setUserId(se.getInvoker().);
			log.setService(se.getInvoker().getInterface().getSimpleName());
			log.setMethod(se.getInvocation().getMethodName());
			log.setResult(se.getResult().toString());
			log.setUsedTimes(se.getUsedTime());

			logRepository.save(log);

		} else if (event instanceof LoginEvent) {
			LoginEvent le = (LoginEvent) event;

			Log log = new Log();

			log.setClient(le.getSource());
			log.setLdate(new Date(le.getLoginTime()));

			log.setService("SpringSecurity");
			log.setMethod("Login");
			if (le.getAuthSuccess())
				log.setResult("用户" + le.getUsername() + "登录成功");
			else
				log.setResult("用户" + le.getUsername() + "使用密码"
						+ le.getPassword() + "尝试登录失败");

			log.setUsedTimes(0l);

			logRepository.save(log);
		}
	}

}
