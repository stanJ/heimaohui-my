package com.xa3ti.blackcat.business.service.impl;

import java.util.Map;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.base.util.QuerySqlExecutor;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.annotation.TableJoin;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.xa3ti.blackcat.business.entity.Feedback;
import com.xa3ti.blackcat.business.repository.FeedbackRepository;
import com.xa3ti.blackcat.business.service.FeedbackService;
import com.xa3ti.blackcat.base.constant.BaseConstant;


@Service("FeedbackService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class FeedbackServiceImpl extends BaseService implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public XaResult<Page<Feedback>> findFeedbackNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Feedback.class.getName(), QueryDao.class);
		Page<Feedback> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = feedbackRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Feedback.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Feedback.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Feedback.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<Feedback>> xr = new XaResult<Page<Feedback>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Feedback>> findFeedbackEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Feedback.class.getName(), QueryDao.class);
		Page<Feedback> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = feedbackRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Feedback.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Feedback.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Feedback.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<Feedback>> xr = new XaResult<Page<Feedback>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Feedback>> findFeedbackVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Feedback.class.getName(), QueryDao.class);
		Page<Feedback> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = feedbackRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Feedback.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Feedback.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Feedback.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<Feedback>> xr = new XaResult<List<Feedback>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Feedback> createFeedback(String userId,Feedback model) throws BusinessException {

		XaResult<Feedback> xr = new XaResult<Feedback>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		Feedback obj = feedbackRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Feedback> updateFeedback(String userId,Feedback model) throws BusinessException {
		Feedback obj = feedbackRepository.findOne(model.getTid());
		XaResult<Feedback> xr = new XaResult<Feedback>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setUserId(model.getUserId());
			 						 			     obj.setFdate(model.getFdate());
			 						 			     obj.setContent(model.getContent());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = feedbackRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Feedback> operateFeedback(
			String userId,Long modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Feedback obj = feedbackRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Feedback> xr = new XaResult<Feedback>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = feedbackRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Feedback> findFeedback(Long modelId) throws BusinessException {
		Feedback obj = feedbackRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Feedback> xr = new XaResult<Feedback>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Feedback> multiOperateFeedback(
			String userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Feedback> xr = new XaResult<Feedback>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Feedback obj = feedbackRepository.findByTidAndStatusNot(Long.parseLong(id),status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = feedbackRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Feedback obj = feedbackRepository.findByTidAndStatusNot(Long.parseLong(modelIds),status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = feedbackRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
