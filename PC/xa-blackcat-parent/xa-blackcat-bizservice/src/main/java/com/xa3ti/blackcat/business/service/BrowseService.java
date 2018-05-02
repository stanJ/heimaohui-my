package com.xa3ti.blackcat.business.service;

import java.util.Date;
import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;

import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.business.entity.Browse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface BrowseService {
	/**
	 *	通过编号查询实体
	 */
	public XaResult<Browse> findBrowse( String  modelId) throws BusinessException;
	
	/**
	 *	分页查询实体,没有删除的对象集
	 */
	public XaResult<Page<Browse>> findBrowseNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	分页查询实体,status==1的对象集
	 */
	public XaResult<Page<Browse>> findBrowseEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 *	创建对象
	 */
	public XaResult<Browse> createBrowse( String  userId,Browse model) throws BusinessException;
	
	/**
	 *	修改对象
	 */
	public XaResult<Browse> updateBrowse( String  userId,Browse model) throws BusinessException;
	
	/**
	 *	操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Browse> operateBrowse( String  userId, String  modelId,Integer status) throws BusinessException;
	
	/**
	 *	批量操作对象,如锁定、删除、解锁等,参考XaConstant.Status
	 */
	public XaResult<Browse> multiOperateBrowse( String  userId,String modelIds,Integer status) throws BusinessException;
	
	/**
	 *	查询VO对象结果集
	 */
	public XaResult<List<Browse>> findBrowseVoByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	
	public Integer getBrowseNums(String uid,Date curDate,Integer btype)  throws BusinessException;
	
	public Boolean checkBrowsed(String uid,Date curDate,Integer btype,String modelId)  throws BusinessException;

	boolean checkCanBrowsed(Browse browse,Integer tokenType) throws BusinessException;

	public boolean isValidataUrl(String url,Integer tokenType) throws BusinessException;

	void checkSaveBrowsed(Browse browse) throws BusinessException;

	Map<String,Object> findUserBrowseInfo(Browse browse,Integer  tokenType) throws BusinessException;

	public Map<String,Object> findUserBrowseParentInfo(String url,String userId, Integer type) throws BusinessException;



	public void exportBrowseEQStatusByFilter(Integer status, Map<String, Object> filterParams,
											 Pageable pageable, HttpServletRequest request, HttpServletResponse response) throws BusinessException;
}
