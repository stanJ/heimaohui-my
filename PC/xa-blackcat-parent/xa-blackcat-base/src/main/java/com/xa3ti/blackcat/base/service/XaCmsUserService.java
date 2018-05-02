package com.xa3ti.blackcat.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.entity.XaCmsRole;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.vo.MenuData;

/**
 * @Title: XaCmsUserService.java
 * @Package com.xa3ti.hhrz.business.service
 * @Description: 基本用户信息Service
 * @author hchen
 * @date 2014年8月1日 下午5:03:34
 * @version V1.0
 */
public interface XaCmsUserService {

	/**
	 * 
	 * @Title: saveCmsUser
	 * @Description: 保存一个用户,并保存角色
	 * @param @param xaCmsUser
	 * @param @return    
	 * @return XaCmsUser
	 */
	XaResult<XaCmsUser> saveCmsUser(XaCmsUser xaCmsUser) throws BusinessException;

	/**
	 * 根据条件分页查询用户列表
	 * @Title: getCmsUserByCondition
	 * @param filterParams
	 * @param pageable
	 * @param @return    
	 * @return Page<XaCmsUser>
	 */
	Page<XaCmsUser> getCmsUserByCondition(Map<String, Object> filterParams,Pageable pageable) throws BusinessException;
	
	/**
	 * 根据ID删除一个用户，假删
	 * @Title: delCmsUserById
	 * @param id
	 * @return boolean
	 */
	void delCmsUserByIds(String uids) throws BusinessException;
	
	void operateCmsUserById(String id, int status) throws BusinessException;
	
	/**
	 * 修改用户密码
	 * @Title: updateCmsUserPassword
	 * @param uid 用户主键
	 * @param oldPassword	旧密码
	 * @param newPassword	新密码
	 * @return int	1表示修改成功；2表示修改失败，失败的原因是由于旧密码不对，不能修改
	 */
	int updateCmsUserPassword(String uid,String oldPassword,String newPassword) throws BusinessException;
	
	
	/**
	 * 修改除密码以外的用户信息
	 * @Title: updateCmsUser
	 * @param xaCmsUser
	 * @return int 1表示修改成功，0表示修改失败。
	 */
	XaResult<XaCmsUser> updateCmsUserNotPassword(XaCmsUser xaCmsUser) throws BusinessException;
	
	
	/**
	 * @Title: getMyXaCmsRoleListByUserId
	 * @Description: 根据用户ID获取用户的角色列表
	 * @param uid
	 * @return List<XaCmsRole>
	 */
	List<XaCmsRole>  getMyXaCmsRoleListByUserId(String uid) throws BusinessException;
	
	
	/**
	 * @Title: getMyXaCmsResourceListByUserId
	 * @Description: 根据用户ID获取用户的资源列表
	 * @param uid
	 * @return List<XaCmsResource>
	 */
	List<XaCmsResource> getMyXaCmsResourceListByUserId(String uid) throws BusinessException;
	
	
	/**
	 * @Title: saveUserRoleList
	 * @Description: 修改或保存用户对应的角色，操作时，先删除用户之前的所有角色，然后再添加所有角色记录
	 * @param uid 用户ID
	 * @param roleIds 角色ID的数组
	 * @return int 1表示增加或修改成功，其它表示修改失败。
	 */
	int saveUserRoleList(String uid,Integer[] roleIds) throws BusinessException;
	
	
	
	/**
	 * @Title: getUserAndRole
	 * @Description: 根据用户ID，查询该用户的信息，和用户角色关系，以及所有可用的角色
	 * @param userId
	 * @return    
	 */
	Map<String, Object>  getUserAndRole(String userId) throws BusinessException;
	
	
	/**
	 * @Title: findXaCmsUserByUserName
	 * @Description: 根据用户名查找某个用户
	 * @param userName 用户名
	 * @param status 用户状态
	 * @return    
	 */
	XaCmsUser findXaCmsUserByUserName(String userName,int status) throws BusinessException;
	
	
	/**
	 * @Title: createUserResourceByUserId
	 * @Description: 根据用户ID查询相应的菜单资源
	 * @param userId 用户ID
	 * @return    
	 */
	MenuData createUserResourceByUserId(String userId) throws BusinessException;

	Boolean findUserNameExists(String userName) throws BusinessException;

	XaCmsUser findUserNameExists(String userName, String pw)throws BusinessException;
}

