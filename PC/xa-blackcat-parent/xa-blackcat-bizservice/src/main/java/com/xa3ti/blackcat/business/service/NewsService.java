package com.xa3ti.blackcat.business.service;

import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;
import com.xa3ti.blackcat.base.util.Pageable;

import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.business.entity.News;


public interface NewsService {
	/**
	 *	通过编号查询实体
	 */
	public XaResult<News> findNews( String  modelId) throws BusinessException;
	
	/**
	 *	分页查询实体,没有删除的对象集
	 */
	public XaResult<Page<News>> findNewsNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	分页查询实体,status==1的对象集
	 */
	public XaResult<Page<News>> findNewsEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	创建对象
	 */
	public XaResult<News> createNews( String  userId,News model) throws BusinessException;
	
	/**
	 *	修改对象
	 */
	public XaResult<News> updateNews( String  userId,News model) throws BusinessException;
	
	/**
	 *	操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<News> operateNews( String  userId, String  modelId,Integer status) throws BusinessException;
	
	/**
	 *	批量操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<News> multiOperateNews( String  userId,String modelIds,Integer status) throws BusinessException;
	
	/**
	 *	查询VO对象结果集
	 */
	public XaResult<List<News>> findNewsVoByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
}
