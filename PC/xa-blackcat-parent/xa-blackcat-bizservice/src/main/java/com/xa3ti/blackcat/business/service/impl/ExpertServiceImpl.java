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
import com.xa3ti.blackcat.business.entity.Expert;
import com.xa3ti.blackcat.business.repository.ExpertRepository;
import com.xa3ti.blackcat.business.service.ExpertService;
import com.xa3ti.blackcat.business.util.Encriptor;
import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("ExpertService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class ExpertServiceImpl extends BaseService implements ExpertService {
	public static Logger  log = Logger.getLogger(ExpertServiceImpl.class);

	@Autowired
	private ExpertRepository expertRepository;
	
	
	@Override
	public XaResult<Page<Expert>> findExpertNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Expert.class.getName(), QueryDao.class);
		Page<Expert> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = expertRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Expert.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Expert.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Expert.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<Expert>> xr = new XaResult<Page<Expert>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Expert>> findExpertEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Expert.class.getName(), QueryDao.class);
		Page<Expert> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = expertRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Expert.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Expert.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Expert.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<Expert>> xr = new XaResult<Page<Expert>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Expert>> findExpertVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Expert.class.getName(), QueryDao.class);
		Page<Expert> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = expertRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Expert.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Expert.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Expert.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<Expert>> xr = new XaResult<List<Expert>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Expert> createExpert( String  userId,Expert model) throws BusinessException {

		XaResult<Expert> xr = new XaResult<Expert>();
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
		
		model=(Expert) Encriptor.encriptModel(model,propList);//加密


		if(!StringUtil.isBlank(model.getName())){
			Expert expert=expertRepository.findExpertByName(model.getName());
			if(expert!=null)
				throw new BusinessException("专家"+model.getName()+"已经存在,不能创建专家");
		}


		Expert obj = expertRepository.save(model);

		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Expert> updateExpert( String  userId,Expert model) throws BusinessException {
		Expert obj = expertRepository.findOne(model.getTid());
		XaResult<Expert> xr = new XaResult<Expert>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setName(model.getName());
			 						 			     obj.setBirth(model.getBirth());
			 						 			     obj.setContact(model.getContact());
			 						 			     obj.setProvince(model.getProvince());
			 			     obj.setCity(model.getCity());
			 			     obj.setDistrict(model.getDistrict());
			 						 			     obj.setGender(model.getGender());
			 						 			     obj.setCertificate(model.getCertificate());
			 						 			     obj.setEmail(model.getEmail());
			 						 			     obj.setWechat(model.getWechat());
			 						 			     obj.setCategory(model.getCategory());
			 						 			     obj.setSocialFunction(model.getSocialFunction());
			 						 			     obj.setComment(model.getComment());
			 						 			     obj.setFilePath(model.getFilePath());
			 						 			     obj.setOtherCategory(model.getOtherCategory());
			                                         obj.setFileName(model.getFileName());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			
			List<String> propList=new ArrayList<String>();
			propList.add("email");
			propList.add("wechat");
			propList.add("certificate");
			propList.add("contact");
			
			obj=(Expert)Encriptor.encriptModel(obj,propList);//加密


			if(!StringUtil.isBlank(obj.getName())){
				Expert expert=expertRepository.findExpertByName(obj.getName());
				if(expert!=null&&!expert.getTid().equals(obj.getTid()))
					throw new BusinessException("专家"+obj.getName()+"已经存在,不能更新专家");
			}


			obj = expertRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	public XaResult<Expert> updateExpertModifyTime(String id, Date modifyTime, String modifyDesc, String modifyUserId) throws BusinessException {
		Expert obj = expertRepository.findOne(id);
		XaResult<Expert> xr = new XaResult<Expert>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setModifyDescription(modifyDesc);
			obj.setModifyTime(com.xa3ti.blackcat.base.util.DateUtil.DateToString(modifyTime, "yyyy-MM-dd HH:mm:ss"));
			obj.setModifyUser(modifyUserId);
			obj = expertRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Expert> operateExpert(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Expert obj = expertRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Expert> xr = new XaResult<Expert>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = expertRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Expert> findExpert( String  modelId) throws BusinessException {
		Expert obj = expertRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Expert> xr = new XaResult<Expert>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
			
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}
	
	
	@Override
	public XaResult<Map<String,Object>> findExpertJoined( String  modelId) throws BusinessException {
		XaResult<Map<String,Object>> xr=new XaResult<Map<String,Object>>();
		
		Map<String, SearchFilter> filters = new HashMap<String,SearchFilter>();
		filters.put("status", new SearchFilter("status", Operator.EQ, XaConstant.Status.valid));
		filters.put("tid", new SearchFilter("tid", Operator.EQ, modelId));
		
		Page<Expert> expertPage = QuerySqlExecutor.find(filters.values(), Expert.class,
				null);
		
		
		List<Expert> list=expertPage.getContent();
		
		if(list!=null&&list.size()>0){
			xr.setObject(ClassUtil.transBean2Map(list.get(0)));
		}else{
			xr.setCode(0);
			xr.setMessage(XaConstant.Message.object_not_find);
			xr.setObject(new HashMap<String,Object>());
		}
		
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Expert> multiOperateExpert(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Expert> xr = new XaResult<Expert>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Expert obj = expertRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = expertRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Expert obj = expertRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = expertRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
