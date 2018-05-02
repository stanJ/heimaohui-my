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
import com.xa3ti.blackcat.business.entity.Work;
import com.xa3ti.blackcat.business.repository.ExpertRepository;
import com.xa3ti.blackcat.business.repository.WorkRepository;
import com.xa3ti.blackcat.business.service.WorkService;
import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("WorkService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class WorkServiceImpl extends BaseService implements WorkService {
	public static Logger  log = Logger.getLogger(WorkServiceImpl.class);

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private ExpertRepository expertRepository;

	@Override
	public XaResult<Page<Work>> findWorkNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Work.class.getName(), QueryDao.class);
		Page<Work> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = workRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Work.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Work.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Work.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<Work>> xr = new XaResult<Page<Work>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Work>> findWorkEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Work.class.getName(), QueryDao.class);
		Page<Work> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = workRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Work.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Work.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Work.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<Work>> xr = new XaResult<Page<Work>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Work>> findWorkVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Work.class.getName(), QueryDao.class);
		Page<Work> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = workRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Work.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Work.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Work.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<Work>> xr = new XaResult<List<Work>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Work> createWork( String  userId,Work model) throws BusinessException {

		XaResult<Work> xr = new XaResult<Work>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");

		if(!StringUtil.isBlank(model.getExpertName())){
			Expert expert=expertRepository.findExpertByName(model.getExpertName());
			if(expert!=null)
				model.setExpertId(expert.getTid());
		}

		Work obj = workRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Work> updateWork( String  userId,Work model) throws BusinessException {
		Work obj = workRepository.findOne(model.getTid());
		XaResult<Work> xr = new XaResult<Work>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setCustomerId(model.getCustomerId());
			 						 			     obj.setSerial(model.getSerial());
			 						 			     obj.setLinker(model.getLinker());
			 						 			     obj.setExpertName(model.getExpertName());
			 						 			     obj.setActionDate(model.getActionDate());
			 						 			     obj.setActionAddress(model.getActionAddress());
			 						 			     obj.setComment(model.getComment());
			 						 			     obj.setPics(model.getPics());
			 						 			     obj.setContractPrice(model.getContractPrice());
			 						 			   obj.setCreatorName(model.getCreatorName());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);

			if(!StringUtil.isBlank(obj.getExpertName())){
				Expert expert=expertRepository.findExpertByName(obj.getExpertName());
				if(expert!=null)
					obj.setExpertId(expert.getTid());
			}


			obj = workRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Work> operateWork(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Work obj = workRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Work> xr = new XaResult<Work>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = workRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Work> findWork( String  modelId) throws BusinessException {
		Work obj = workRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Work> xr = new XaResult<Work>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Work> multiOperateWork(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Work> xr = new XaResult<Work>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Work obj = workRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = workRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Work obj = workRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = workRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
