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
import com.xa3ti.blackcat.business.entity.LectureContent;
import com.xa3ti.blackcat.business.repository.LectureContentRepository;
import com.xa3ti.blackcat.business.service.LectureContentService;

import com.xa3ti.blackcat.base.constant.BaseConstant;


@Service("LectureContentService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class LectureContentServiceImpl extends BaseService implements LectureContentService {
	public static Logger  log = Logger.getLogger(LectureContentServiceImpl.class);

	@Autowired
	private LectureContentRepository lectureContentRepository;

	@Override
	public XaResult<Page<LectureContent>> findLectureContentNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				LectureContent.class.getName(), QueryDao.class);
		Page<LectureContent> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			page  = lectureContentRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), LectureContent.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, LectureContent.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), LectureContent.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
		
		
		 
		XaResult<Page<LectureContent>> xr = new XaResult<Page<LectureContent>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<LectureContent>> findLectureContentEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				LectureContent.class.getName(), QueryDao.class);
		Page<LectureContent> page = null;
		if (BaseConstant.DAO.JPA.equals(an.method())){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.valid;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = lectureContentRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), LectureContent.class), pageable);
		
		}

		else if (BaseConstant.DAO.DYNASQL.equals(an.method())){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, LectureContent.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), LectureContent.class,
						pageable);
			}
		}
		else if (BaseConstant.DAO.MYBATIS.equals(an.method())){
			//
		}
					
		XaResult<Page<LectureContent>> xr = new XaResult<Page<LectureContent>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<LectureContent>> findLectureContentVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				LectureContent.class.getName(), QueryDao.class);
		Page<LectureContent> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			page  = lectureContentRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), LectureContent.class), pageable);
		
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, LectureContent.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.valid;
				}
				filters.put("status", new SearchFilter("status", Operator.EQ, status));
				
				page = QuerySqlExecutor.find(filters.values(), LectureContent.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			//
		}
					
		XaResult<List<LectureContent>> xr = new XaResult<List<LectureContent>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<LectureContent> createLectureContent( String  userId,LectureContent model) throws BusinessException {

		XaResult<LectureContent> xr = new XaResult<LectureContent>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		LectureContent obj = lectureContentRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<LectureContent> updateLectureContent( String  userId,LectureContent model) throws BusinessException {
		LectureContent obj = lectureContentRepository.findOne(model.getTid());
		XaResult<LectureContent> xr = new XaResult<LectureContent>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setExpertId(model.getExpertId());
			 						 			     obj.setSerial(model.getSerial());
			 						 			     obj.setContent(model.getContent());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = lectureContentRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<LectureContent> operateLectureContent(
			 String  userId, String  modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		LectureContent obj = lectureContentRepository.findByTidAndStatusNot(modelId,status);
		XaResult<LectureContent> xr = new XaResult<LectureContent>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = lectureContentRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<LectureContent> findLectureContent( String  modelId) throws BusinessException {
		LectureContent obj = lectureContentRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<LectureContent> xr = new XaResult<LectureContent>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<LectureContent> multiOperateLectureContent(
			 String  userId,String modelIds,Integer status) throws BusinessException {
		XaResult<LectureContent> xr = new XaResult<LectureContent>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					LectureContent obj = lectureContentRepository.findByTidAndStatusNot( id ,status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = lectureContentRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				LectureContent obj = lectureContentRepository.findByTidAndStatusNot( modelIds ,status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = lectureContentRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
