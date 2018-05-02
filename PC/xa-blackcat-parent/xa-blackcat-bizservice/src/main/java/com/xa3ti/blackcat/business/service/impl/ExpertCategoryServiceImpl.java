package com.xa3ti.blackcat.business.service.impl;

import java.util.Map;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.xa3ti.blackcat.base.util.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.QuerySqlExecutor;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.annotation.TableJoin;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.xa3ti.blackcat.business.entity.ExpertCategory;
import com.xa3ti.blackcat.business.repository.ExpertCategoryRepository;
import com.xa3ti.blackcat.business.service.ExpertCategoryService;

import com.xa3ti.blackcat.base.constant.BaseConstant;


@Service("ExpertCategoryService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class ExpertCategoryServiceImpl extends BaseService implements ExpertCategoryService {
	public static Logger  log = Logger.getLogger(ExpertCategoryServiceImpl.class);

	@Autowired
	private ExpertCategoryRepository expertCategoryRepository;

	@Override
	public XaResult<Page<ExpertCategory>> findExpertCategoryNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				ExpertCategory.class.getName(), QueryDao.class);
		Page<ExpertCategory> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = expertCategoryRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), ExpertCategory.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, ExpertCategory.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), ExpertCategory.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<ExpertCategory>> xr = new XaResult<Page<ExpertCategory>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<ExpertCategory>> findExpertCategoryEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				ExpertCategory.class.getName(), QueryDao.class);
		Page<ExpertCategory> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = expertCategoryRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), ExpertCategory.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, ExpertCategory.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), ExpertCategory.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<ExpertCategory>> xr = new XaResult<Page<ExpertCategory>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<ExpertCategory>> findExpertCategoryVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				ExpertCategory.class.getName(), QueryDao.class);
		Page<ExpertCategory> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = expertCategoryRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), ExpertCategory.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, ExpertCategory.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), ExpertCategory.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<ExpertCategory>> xr = new XaResult<List<ExpertCategory>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<ExpertCategory> createExpertCategory( String  userId,ExpertCategory model) throws BusinessException {

		XaResult<ExpertCategory> xr = new XaResult<ExpertCategory>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		ExpertCategory obj = expertCategoryRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<ExpertCategory> updateExpertCategory( String  userId,ExpertCategory model) throws BusinessException {
		ExpertCategory obj = expertCategoryRepository.findOne(model.getTid());
		XaResult<ExpertCategory> xr = new XaResult<ExpertCategory>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setName(model.getName());
			 						 			     obj.setComment(model.getComment());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = expertCategoryRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<ExpertCategory> operateExpertCategory(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		ExpertCategory obj = expertCategoryRepository.findByTidAndStatusNot(modelId,status);
		XaResult<ExpertCategory> xr = new XaResult<ExpertCategory>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = expertCategoryRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<ExpertCategory> findExpertCategory( String  modelId) throws BusinessException {
		ExpertCategory obj = expertCategoryRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<ExpertCategory> xr = new XaResult<ExpertCategory>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<ExpertCategory> multiOperateExpertCategory(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<ExpertCategory> xr = new XaResult<ExpertCategory>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					ExpertCategory obj = expertCategoryRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = expertCategoryRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				ExpertCategory obj = expertCategoryRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = expertCategoryRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
