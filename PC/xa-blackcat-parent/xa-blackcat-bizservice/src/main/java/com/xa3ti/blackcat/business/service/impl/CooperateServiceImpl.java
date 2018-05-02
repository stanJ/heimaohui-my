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
import com.xa3ti.blackcat.business.entity.Cooperate;
import com.xa3ti.blackcat.business.entity.Customer;
import com.xa3ti.blackcat.business.repository.CooperateRepository;
import com.xa3ti.blackcat.business.repository.CustomerRepository;
import com.xa3ti.blackcat.business.service.CooperateService;
import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("CooperateService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class CooperateServiceImpl extends BaseService implements CooperateService {
	public static Logger  log = Logger.getLogger(CooperateServiceImpl.class);

	@Autowired
	private CooperateRepository cooperateRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public XaResult<Page<Cooperate>> findCooperateNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Cooperate.class.getName(), QueryDao.class);
		Page<Cooperate> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = cooperateRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Cooperate.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Cooperate.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Cooperate.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<Cooperate>> xr = new XaResult<Page<Cooperate>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Cooperate>> findCooperateEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Cooperate.class.getName(), QueryDao.class);
		Page<Cooperate> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = cooperateRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Cooperate.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Cooperate.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Cooperate.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<Cooperate>> xr = new XaResult<Page<Cooperate>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Cooperate>> findCooperateVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Cooperate.class.getName(), QueryDao.class);
		Page<Cooperate> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = cooperateRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Cooperate.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Cooperate.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), Cooperate.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<Cooperate>> xr = new XaResult<List<Cooperate>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Cooperate> createCooperate( String  userId,Cooperate model) throws BusinessException {

		XaResult<Cooperate> xr = new XaResult<Cooperate>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");


		if(!StringUtil.isBlank(model.getCustomerName())){
			Customer customer=customerRepository.findCustomerByName(model.getCustomerName());
			if(customer!=null)
				model.setCustomerId(customer.getTid());
		}


		Cooperate obj = cooperateRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Cooperate> updateCooperate( String  userId,Cooperate model) throws BusinessException {
		Cooperate obj = cooperateRepository.findOne(model.getTid());
		XaResult<Cooperate> xr = new XaResult<Cooperate>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setExpertId(model.getExpertId());
			 						 			     obj.setCustomerName(model.getCustomerName());
			 						 			     obj.setActionDate(model.getActionDate());
			 						 			     obj.setActionAddress(model.getActionAddress());
			 						 			     obj.setContractPrice(model.getContractPrice());
			 						 			     obj.setCostPrice(model.getCostPrice());
			 						 			     obj.setExpertContactor(model.getExpertContactor());
			 						 			     obj.setComment(model.getComment());
			 						 			     obj.setPics(model.getPics());
			 						 			     obj.setCreatorName(model.getCreatorName());
			 						 			     obj.setSerial(model.getSerial());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);

			if(!StringUtil.isBlank(obj.getCustomerName())){
				Customer customer=customerRepository.findCustomerByName(obj.getCustomerName());
				if(customer!=null)
					obj.setCustomerId(customer.getTid());
			}

			obj = cooperateRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Cooperate> operateCooperate(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Cooperate obj = cooperateRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Cooperate> xr = new XaResult<Cooperate>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = cooperateRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Cooperate> findCooperate( String  modelId) throws BusinessException {
		Cooperate obj = cooperateRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Cooperate> xr = new XaResult<Cooperate>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Cooperate> multiOperateCooperate(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Cooperate> xr = new XaResult<Cooperate>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Cooperate obj = cooperateRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = cooperateRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Cooperate obj = cooperateRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = cooperateRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
