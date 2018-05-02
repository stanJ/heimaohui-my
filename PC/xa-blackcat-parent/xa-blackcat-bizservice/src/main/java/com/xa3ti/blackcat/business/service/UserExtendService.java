package com.xa3ti.blackcat.business.service;

import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;

import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.business.entity.UserExtend;
import com.xa3ti.blackcat.business.model.UserRegister;


public interface UserExtendService {
	/**
	 *	通过编号查询实体
	 */
	public XaResult<UserExtend> findUserExtend( String  modelId) throws BusinessException;
	
	/**
	 *	分页查询实体,没有删除的对象集
	 */
	public XaResult<Page<UserExtend>> findUserExtendNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	分页查询实体,status==1的对象集
	 */
	public XaResult<Page<UserExtend>> findUserExtendEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	创建对象
	 */
	public XaResult<UserExtend> createUserExtend( String  userId,UserExtend model) throws BusinessException;
	
	/**
	 *	修改对象
	 */
	public XaResult<UserExtend> updateUserExtend( String  userId,UserExtend model) throws BusinessException;
	
	/**
	 *	操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<UserExtend> operateUserExtend( String  userId, String  modelId,Integer status) throws BusinessException;
	
	/**
	 *	批量操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<UserExtend> multiOperateUserExtend( String  userId,String modelIds,Integer status) throws BusinessException;
	
	/**
	 *	查询VO对象结果集
	 */
	public XaResult<List<UserExtend>> findUserExtendVoByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	
	/**
	 *	注册用户
	 */
	public XaResult<UserExtend> registerUser(String userId,UserRegister userRegister) throws BusinessException;
	
	/**
	 *	修改用户
	 */
	public XaResult<UserExtend> modifyUser(String userId,UserRegister userRegister) throws BusinessException;
	
	/**
	 *	修改用户
	 */
	public XaResult<UserExtend> resetPassword(String userId,UserRegister userRegister) throws BusinessException;
	
	
	
	/**
	 *	修改用户
	 */
	public XaResult<UserExtend> deleteUser(String userId,String userName) throws BusinessException;
	
	
	public XaResult<UserRegister> findUser(String userId,String userName) throws BusinessException;
	
}
