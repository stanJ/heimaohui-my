package com.xa3ti.blackcat.business.service;

import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.business.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CustomerService {
	/**
	 *	通过编号查询实体
	 */
	public XaResult<Customer> findCustomer( String  modelId) throws BusinessException;
	
	public  XaResult<Map<String,Object>> findCustomerJoined( String  modelId) throws BusinessException;
	
	/**
	 *	分页查询实体,没有删除的对象集
	 */
	public XaResult<Page<Customer>> findCustomerNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	分页查询实体,status==1的对象集
	 */
	public XaResult<Page<Customer>> findCustomerEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	创建对象
	 */
	public XaResult<Customer> createCustomer( String  userId,Customer model) throws BusinessException;
	
	/**
	 *	修改对象
	 */
	public XaResult<Customer> updateCustomer( String  userId,Customer model) throws BusinessException;


	/**
	 *	修改对象
	 */
	public XaResult<Customer> updateCustomerModifyTime(String id, Date modifyTime, String modifyDesc, String modifyUserId) throws BusinessException;

	/**
	 *	操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Customer> operateCustomer( String  userId, String  modelId,Integer status) throws BusinessException;
	
	/**
	 *	批量操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Customer> multiOperateCustomer( String  userId,String modelIds,Integer status) throws BusinessException;
	
	/**
	 *	查询VO对象结果集
	 */
	public XaResult<List<Customer>> findCustomerVoByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
}
