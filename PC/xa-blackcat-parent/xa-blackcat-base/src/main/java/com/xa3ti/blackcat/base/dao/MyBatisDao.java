/**
 * 
 */
package com.xa3ti.blackcat.base.dao;

import java.io.Serializable;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MyBatis的Dao基类
 * @author jacopo
 */
public class MyBatisDao extends SqlSessionDaoSupport{
	
	@Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
 
    protected <S> S getMapper(Class<S> clazz) {
        return getSqlSession().getMapper(clazz);
    }
    
	public boolean save(String key, Object object) {
		int count = getSqlSession().insert(key, object);
		return count > 0 ? true : false;
	}
	
	public boolean update(String key , Object object) {
		int count = getSqlSession().update(key, object);
		return count > 0 ? true : false;
	}
	public boolean delete(String key, Serializable id) {
		int count = getSqlSession().delete(key, id);
		return count > 0 ? true : false;
	}

	public boolean delete(String key, Object object) {
		int count = getSqlSession().delete(key, object);
		return count > 0 ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object params) {
		return (T) getSqlSession().selectOne(key, params);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {
		return getSqlSession().selectList(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}
	
	
	
}
