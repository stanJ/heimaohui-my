package com.xa3ti.blackcat.base.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import com.xa3ti.blackcat.base.util.QuerySqlExecutor;
import com.xa3ti.blackcat.base.util.Settings;


/**
 * @Title: BaseService.java
 * @Description: TODO
 * @author hchen
 * @date 2014年8月20日 下午6:55:24
 * @version V1.0
 */
public abstract class BaseService {
	private static Logger log = Logger.getLogger(BaseService.class);
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	
	private static Boolean avoidInject = false;
	static {

		String avoidInjectStr = (String) Settings.getInstance().getString(
				"avoid.sql.inject");
		try {
			avoidInject = Boolean.valueOf(avoidInjectStr);
		} catch (Exception e) {

		}
	}

	public QuerySqlExecutor QuerySqlExecutor = new QuerySqlExecutor(avoidInject);
	
	
	@SuppressWarnings("unchecked")
	public List<T> query(String sql,String resultSetMapping){
		List<T> list = null;
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query query = em.createNativeQuery(sql, resultSetMapping);
			list = query.getResultList();
		}catch (Exception e){
			log.error(e);
		}finally {
			em.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> query(String sql){
		EntityManager em = entityManagerFactory.createEntityManager();
		List<Object[]> list = null;
		try {
			Query query = em.createNativeQuery(sql);
			list = query.getResultList();
		}catch (Exception e){
			log.error(e);
		}finally {
			em.close();
		}
		return list;
	}

	public void executeSqlUpdate(String sql){
		EntityManager em = entityManagerFactory.createEntityManager();
		try{
			Query query= em.createNativeQuery(sql);
			query.executeUpdate();
		}catch (Exception e){
			log.error(e);
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> query(String sql, List<Object> paramList) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createNativeQuery(sql);

		int i = 1;
		for (Object o : paramList) {
			if (o instanceof java.util.Date) {
				query.setParameter(i, (java.util.Date) o,
						javax.persistence.TemporalType.TIMESTAMP);
			} else {
				query.setParameter(i, o);
			}
			i++;
		}

		List<Object[]> list = query.getResultList();
		em.close();
		return list;
	}

}

