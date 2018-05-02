package com.xa3ti.blackcat.business.service.impl;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.service.impl.BaseService;
import com.xa3ti.blackcat.base.tag.DependCheckBoxTag;
import com.xa3ti.blackcat.base.dao.MyBatisDao;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.constant.BaseConstant;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.Pageable;
import com.xa3ti.blackcat.base.util.QuerySqlExecutor;
import com.xa3ti.blackcat.base.util.AnnotationUtil;
import com.xa3ti.blackcat.base.annotation.XALogger;
import com.xa3ti.blackcat.base.annotation.QueryDao;
import com.xa3ti.blackcat.base.annotation.TableJoin;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.SearchFilter.Operator;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.xa3ti.blackcat.business.entity.Aboutus;
import com.xa3ti.blackcat.business.model.AboutusExample;
//import com.xa3ti.blackcat.business.model.AboutusExample;
import com.xa3ti.blackcat.business.repository.AboutusRepository;
import com.xa3ti.blackcat.business.service.AboutusService;

@Service("AboutusService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AboutusServiceImpl extends BaseService implements AboutusService {

	public static Logger  log = Logger.getLogger(AboutusServiceImpl.class);
	
	
	@Autowired
	private AboutusRepository aboutusRepository; //JPA
	
	@Autowired
	private MyBatisDao myBatisDao;//MYBATIS
	
	
	private QuerySqlExecutor querySqlExecutor;//DYNASQL

	@Override
	public XaResult<Page<Aboutus>> findAboutusNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Aboutus.class.getName(), QueryDao.class);
		Page<Aboutus> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.NE, status));
			
			page  = aboutusRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Aboutus.class), pageable);
				
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status!="+status+")";
				page = QuerySqlExecutor.find(null, Aboutus.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				page = QuerySqlExecutor.find(filters.values(), Aboutus.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			AboutusExample ae=new AboutusExample();
			AboutusExample.Criteria c=ae.createCriteria();
			
			ae.setPageable(pageable);
			//拼查询条件
			c.andTelLike("%123%");
			
			
			myBatisDao.getList("com.xa3ti.blackcat.business.dao.AboutusMapper.selectByExample", ae);
			
			page=new PageImpl(ae.getPageable().getEntityList(),pageable,ae.getPageable().getTotalSize());
			
		}
 		
		XaResult<Page<Aboutus>> xr = new XaResult<Page<Aboutus>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<Page<Aboutus>> findAboutusEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		QueryDao an = (QueryDao)AnnotationUtil.getAnnotationPresentForEntity(
				Aboutus.class.getName(), QueryDao.class);
		Page<Aboutus> page = null;
		if (an.method().equals(BaseConstant.DAO.JPA)){
			
			Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
			if(status == null){// 默认显示非删除的所有数据
				status = XaConstant.Status.delete;
			}
			filters.put("status", new SearchFilter("status", Operator.EQ, status));
			
			
			
			page  = aboutusRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Aboutus.class), pageable);
			
			
		}

		else if (an.method().equals(BaseConstant.DAO.DYNASQL)){
			
			String sqlFilter=(String)filterParams.get(BaseConstant.SQLFILTERKEY);
			if(!StringUtils.isBlank(sqlFilter)){ //混合查
				sqlFilter=sqlFilter.substring(1);
				sqlFilter=sqlFilter.substring(0,sqlFilter.length()-1);//去除{}
				sqlFilter="("+sqlFilter+")" +" and (status=="+status+")";
				page = QuerySqlExecutor.find(null, Aboutus.class,
						pageable,sqlFilter);
			}else{//传统and查
				Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
				if(status == null){// 默认显示非删除的所有数据
					status = XaConstant.Status.delete;
				}
				filters.put("status", new SearchFilter("status", Operator.NE, status));
				
				
				
				page = QuerySqlExecutor.find(filters.values(), Aboutus.class,
						pageable);
			}
		}
		else if (an.method().equals(BaseConstant.DAO.MYBATIS)){
			AboutusExample ae=new AboutusExample();
			AboutusExample.Criteria c=ae.createCriteria();
			
			ae.setPageable(pageable);
			/**拼查询条件
			c.andVersionnoEqualTo("")
			*/
			
			myBatisDao.getList("com.xa3ti.blackcat.business.dao.AboutusMapper.selectByExample", ae);
			
			page=new PageImpl(ae.getPageable().getEntityList(),pageable,ae.getPageable().getTotalSize());
			
		}
 		
		
		
		 
		XaResult<Page<Aboutus>> xr = new XaResult<Page<Aboutus>>();
		xr.setObject(page);
		return xr;
	}

	@Override
	public XaResult<List<Aboutus>> findAboutusVoByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		if(status == null){// 默认为有效状态
			status = XaConstant.Status.valid;
		}
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		filters.put("status", new SearchFilter("status", Operator.EQ, status));
		
		Boolean isJoined = AnnotationUtil.isAnnotationPresentForEntity(
				Aboutus.class.getName(), TableJoin.class);
		Page<Aboutus> page = null;
		if (!isJoined)
			page = page = aboutusRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Aboutus.class), pageable);

		else
			page = QuerySqlExecutor.find(filters.values(), Aboutus.class,
					pageable);
					
		XaResult<List<Aboutus>> xr = new XaResult<List<Aboutus>>();
		xr.setObject(page.getContent());
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Aboutus> createAboutus(String userId,Aboutus model) throws BusinessException {

		XaResult<Aboutus> xr = new XaResult<Aboutus>();
		//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
		if(userId != null){
			model.setCreateUser(userId);
			model.setModifyUser(userId);
		}
		model.setModifyDescription("初始创建");
		Aboutus obj = aboutusRepository.save(model);
		xr.setObject(obj);
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Aboutus> updateAboutus(String userId,Aboutus model) throws BusinessException {
		Aboutus obj = aboutusRepository.findOne(model.getTid());
		XaResult<Aboutus> xr = new XaResult<Aboutus>();
		if (XaUtil.isNotEmpty(obj)) {
						 			     obj.setLogo(model.getLogo());
			 						 			     obj.setPics(model.getPics());
			 						 			     obj.setVersionno(model.getVersionno());
			 						 			     obj.setTel(model.getTel());
			 						 			     obj.setContent(model.getContent());
			 						//此处应该加入对userId的判断,由于用户表也是后期创建,所以这里自行添加
			obj.setModifyUser(userId);
			obj = aboutusRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Aboutus> operateAboutus(
			String userId,Long modelId,Integer status) throws BusinessException {
		if(status == null){
			status = XaConstant.Status.delete;
		}
		Aboutus obj = aboutusRepository.findByTidAndStatusNot(modelId,status);
		XaResult<Aboutus> xr = new XaResult<Aboutus>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(status);
			obj.setModifyUser(userId);
			obj = aboutusRepository.save(obj);
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Aboutus> findAboutus(Long modelId) throws BusinessException {
		Aboutus obj = aboutusRepository.findByTidAndStatusNot(modelId,XaConstant.Status.delete);
		XaResult<Aboutus> xr = new XaResult<Aboutus>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	@XALogger(need=true)
	public XaResult<Aboutus> multiOperateAboutus(
			String userId,String modelIds,Integer status) throws BusinessException {
		XaResult<Aboutus> xr = new XaResult<Aboutus>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			if(StringUtils.indexOf(modelIds, ",") > 0){
				String[] ids = modelIds.split(",");
				for(String id : ids){
					Aboutus obj = aboutusRepository.findByTidAndStatusNot(Long.parseLong(id),status);
					if (XaUtil.isNotEmpty(obj)) {
						obj.setStatus(status);
						obj.setModifyUser(userId);
						obj = aboutusRepository.save(obj);
					} else {
						throw new BusinessException(XaConstant.Message.object_not_find);
					}
				}
			}
			else{
				Aboutus obj = aboutusRepository.findByTidAndStatusNot(Long.parseLong(modelIds),status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj.setModifyUser(userId);
					obj = aboutusRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	
	
}
