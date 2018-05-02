package com.xa3ti.blackcat.base.util;


import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * Created by jackson.liu on 2017/2/24.
 */
public class DBHelper {
    private static final Logger log = Logger.getLogger(DBHelper.class);
    static Connection conn = null;
    static String url = Settings.getInstance().getDbString("jdbc.url");
    static String username = Settings.getInstance().getDbString("jdbc.username");
    static String password = Settings.getInstance().getDbString("jdbc.password");

    private static void getConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw e;
        }
    }


    private static void closeConnection() throws Exception {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            throw e;
        }
    }


    public static Integer count(String countSql) {
        Integer count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            log.info(countSql);
            getConnection();
            //用JDBC 查总数
            ps = conn.prepareStatement(countSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = Integer.valueOf(rs.getInt(1));
                break;
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                closeConnection();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return count;
    }

    public static Map<String, Object> load(String sql, Object... params) throws SQLException ,Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> valueMap = null;
        try {
            log.info(sql);
            getConnection();
            //用JDBC 查总数
            ps = conn.prepareStatement(sql);
            if (null != params && params.length>0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            if(null != rs) {
                valueMap = new HashMap<String, Object>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int fieldCount = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= fieldCount; i++) {
                        String fieldClassName = rsmd.getColumnClassName(i);
                        String fieldName = rsmd.getColumnName(i);
                        valueMap.put(fieldName, rs.getObject(fieldName));

                    }
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                closeConnection();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return valueMap;
    }

    public static List<Map<String, Object>> query(String sql, Object... params) throws SQLException ,Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = null;
        try {
            log.info(sql);
            getConnection();
            //用JDBC 查总数
            ps = conn.prepareStatement(sql);
            if (null != params && params.length>0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            if(null != rs) {
                list = new ArrayList<Map<String, Object>>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int fieldCount = rsmd.getColumnCount();
                int index = 0 ;
                while (rs.next()) {
                    Map<String, Object> valueMap = new HashMap<String, Object>();
                    for (int i = 1; i <= fieldCount; i++) {
                        String fieldClassName = rsmd.getColumnClassName(i);
                        String fieldName = rsmd.getColumnName(i);
                        valueMap.put(fieldName, rs.getObject(fieldName));

                    }
                    list.add(index,valueMap);
                    index++;
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                closeConnection();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return list;
    }


    /**
     * 将ResultSet结果集中的记录映射到Map对象中.
     *
     * @param fieldClassName 是JDBC API中的类型名称,
     * @param fieldName      是字段名，
     * @param rs             是一个ResultSet查询结果集,
     * @throws SQLException
     */
    private static Object recordMappingToMap(String fieldClassName, String fieldName, ResultSet rs)
            throws SQLException {
        Object obj = null;
        fieldName = fieldName.toLowerCase();

        // 优先规则：常用类型靠前
        if (fieldClassName.equals("java.lang.String")) {
           obj = rs.getString(fieldName);

        } else if (fieldClassName.equals("java.lang.Integer")) {
            obj = rs.getInt(fieldName);

        } else if (fieldClassName.equals("java.lang.Long")) {
            obj = rs.getLong(fieldName);

        } else if (fieldClassName.equals("java.lang.Boolean")) {
            obj = rs.getBoolean(fieldName);

        } else if (fieldClassName.equals("java.lang.Short")) {
            obj = rs.getShort(fieldName);

        } else if (fieldClassName.equals("java.lang.Float")) {
            obj = rs.getFloat(fieldName);

        } else if (fieldClassName.equals("java.lang.Double")) {
            obj = rs.getDouble(fieldName);

        } else if (fieldClassName.equals("java.sql.Timestamp")) {
            obj = rs.getTimestamp(fieldName);

        } else if (fieldClassName.equals("java.sql.Date") || fieldClassName.equals("java.util.Date")) {
            obj = rs.getDate(fieldName);

        } else if (fieldClassName.equals("java.sql.Time")) {
            obj = rs.getTime(fieldName);

        } else if (fieldClassName.equals("java.lang.Byte")) {
            obj = rs.getByte(fieldName);

        } else if (fieldClassName.equals("[B") || fieldClassName.equals("byte[]")) {
            // byte[]出现在SQL Server中
            obj = rs.getBytes(fieldName);

        } else if (fieldClassName.equals("java.math.BigDecimal")) {
            obj = rs.getBigDecimal(fieldName);

        } else if (fieldClassName.equals("java.lang.Object") || fieldClassName.equals("oracle.sql.STRUCT")) {
            Object s = rs.getObject(fieldName);

        } else if (fieldClassName.equals("java.sql.Array") || fieldClassName.equals("oracle.sql.ARRAY")) {
            obj = rs.getArray(fieldName);

        } else if (fieldClassName.equals("java.sql.Clob")) {
            obj = rs.getClob(fieldName);

        } else if (fieldClassName.equals("java.sql.Blob")) {
            obj = rs.getBlob(fieldName);

        } else {// 对于其它任何未知类型的处理
            obj = rs.getObject(fieldName);
        }
        return  obj;
    }
}
