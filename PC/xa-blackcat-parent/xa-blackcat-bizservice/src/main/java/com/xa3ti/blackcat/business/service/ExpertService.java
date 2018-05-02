package com.xa3ti.blackcat.business.service;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.business.entity.Expert;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ExpertService {
	/**
	 *	通过编号查询实体
	 */
	public XaResult<Expert> findExpert( String  modelId) throws BusinessException;
	
	public  XaResult<Map<String,Object>> findExpertJoined( String  modelId) throws BusinessException;
	
	/**
	 *	分页查询实体,没有删除的对象集
	 */
	public XaResult<Page<Expert>> findExpertNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	分页查询实体,status==1的对象集
	 */
	public XaResult<Page<Expert>> findExpertEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	创建对象
	 */
	public XaResult<Expert> createExpert( String  userId,Expert model) throws BusinessException;
	
	/**
	 *	修改对象
	 */
	public XaResult<Expert> updateExpert( String  userId,Expert model) throws BusinessException;

	/**
	 *	修改对象
	 */
	public XaResult<Expert> updateExpertModifyTime(String id, Date modifyTime, String modifyDesc, String modifyUserId) throws BusinessException;
	
	/**
	 *	操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Expert> operateExpert( String  userId, String  modelId,Integer status) throws BusinessException;
	
	/**
	 *	批量操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Expert> multiOperateExpert( String  userId,String modelIds,Integer status) throws BusinessException;
	
	/**
	 *	查询VO对象结果集
	 */
	public XaResult<List<Expert>> findExpertVoByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
}
