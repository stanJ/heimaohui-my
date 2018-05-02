package com.xa3ti.blackcat.business.service;

import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;



import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.business.entity.Feedback;


public interface FeedbackService {
	/**
	 *	通过编号查询实体
	 */
	public XaResult<Feedback> findFeedback(Long modelId) throws BusinessException;
	
	/**
	 *	分页查询实体,没有删除的对象集
	 */
	public XaResult<Page<Feedback>> findFeedbackNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	分页查询实体,status==1的对象集
	 */
	public XaResult<Page<Feedback>> findFeedbackEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	创建对象
	 */
	public XaResult<Feedback> createFeedback(String userId,Feedback model) throws BusinessException;
	
	/**
	 *	修改对象
	 */
	public XaResult<Feedback> updateFeedback(String userId,Feedback model) throws BusinessException;
	
	/**
	 *	操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Feedback> operateFeedback(String userId,Long modelId,Integer status) throws BusinessException;
	
	/**
	 *	批量操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Feedback> multiOperateFeedback(String userId,String modelIds,Integer status) throws BusinessException;
	
	/**
	 *	查询VO对象结果集
	 */
	public XaResult<List<Feedback>> findFeedbackVoByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
}
