package com.xa3ti.blackcat.business.service.impl;

import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.util.*;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.business.entity.Contactor;
import com.xa3ti.blackcat.business.repository.ContactorRepository;
import com.xa3ti.blackcat.business.service.ContactorService;
import com.xa3ti.blackcat.business.util.Encriptor;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("ContactorService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class ContactorServiceImpl extends BaseService implements ContactorService {
	public static Logger  log = Logger.getLogger(ContactorServiceImpl.class);

	@Autowired
	private ContactorRepository contactorRepository;

	@Override
	public XaResult<Page<Contactor>> findContactorNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Contactor.class.getName(), QueryDao.class);
		Page<Contactor> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = contactorRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Contactor.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Contactor.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Contactor.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<Contactor>> xr = new XaResult<Page<Contactor>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Contactor>> findContactorEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Contactor.class.getName(), QueryDao.class);
		Page<Contactor> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = contactorRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Contactor.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Contactor.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Contactor.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<Contactor>> xr = new XaResult<Page<Contactor>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Contactor>> findContactorVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Contactor.class.getName(), QueryDao.class);
		Page<Contactor> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = contactorRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Contactor.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Contactor.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Contactor.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<Contactor>> xr = new XaResult<List<Contactor>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Contactor> createContactor( String  userId,Contactor model) throws BusinessException {

		XaResult<Contactor> xr = new XaResult<Contactor>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");

		List<String> propList=new ArrayList<String>();
		propList.add("email");
		propList.add("wechat");
		propList.add("certificate");
		propList.add("contact");

		model=(Contactor) Encriptor.encriptModel(model,propList);//加密

		Contactor obj = contactorRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Contactor> updateContactor( String  userId,Contactor model) throws BusinessException {
		Contactor obj = contactorRepository.findOne(model.getTid());
		XaResult<Contactor> xr = new XaResult<Contactor>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setCustomerId(model.getCustomerId());
			 						 			     obj.setName(model.getName());
			 						 			     obj.setContact(model.getContact());
			 						 			     obj.setWechat(model.getWechat());
			 						 			     obj.setEmail(model.getEmail());
			 						 			     obj.setSerial(model.getSerial());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);


			List<String> propList=new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("certificate");
			propList.add("contact");

			obj=(Contactor) Encriptor.encriptModel(obj,propList);//加密

			obj = contactorRepository.save(obj);

			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Contactor> operateContactor(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Contactor obj = contactorRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Contactor> xr = new XaResult<Contactor>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = contactorRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Contactor> findContactor( String  modelId) throws BusinessException {
		Contactor obj = contactorRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Contactor> xr = new XaResult<Contactor>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Contactor> multiOperateContactor(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Contactor> xr = new XaResult<Contactor>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Contactor obj = contactorRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = contactorRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Contactor obj = contactorRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = contactorRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
