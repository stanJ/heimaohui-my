package com.xa3ti.blackcat.base.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.entity.XaCmsRoleResource;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.repository.XaCmsResourceRepository;
import com.xa3ti.blackcat.base.repository.XaCmsRoleResourceRepository;
import com.xa3ti.blackcat.base.security.XaSecurityMetadataSourceService;
import com.xa3ti.blackcat.base.service.XaCmsResourceService;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.SearchFilter;

/**
 * @Title: XaCmsResourceServiceImple.java
 * @Package com.xa3ti.hhrz.business.service.impl
 * @Description: 资源服务实现类
 * @author hchen
 * @date 2014年8月2日 上午11:08:55
 * @version V1.0
 */
@Service("xaCmsResourceService")
public class XaCmsResourceServiceImple implements XaCmsResourceService {

	private static final Logger log=Logger.getLogger(XaCmsResourceServiceImple.class);
	@Autowired
	private XaCmsResourceRepository xaCmsResourceRepository;
	
	@Autowired
	private XaCmsRoleResourceRepository xaCmsRoleResourceRepository;
	
	public XaCmsResource saveXaCmsResource(XaCmsResource xaCmsResource) {
		return xaCmsResourceRepository.save(xaCmsResource);
	}

	@Override
	@Transactional(readOnly=false)
	public int delXaCmsResource(long rid)  throws BusinessException {
		XaCmsResource xaCmsResource=xaCmsResourceRepository.findOne(rid);
		if(xaCmsResource!=null){
			xaCmsResource.setStatus(XaConstant.ResourcesStatus.status_delete);
			xaCmsResourceRepository.save(xaCmsResource);
			//删资源时，需要将关联该资源的角色的关系删除
			List<XaCmsRoleResource> xcrrList=xaCmsRoleResourceRepository.findRoleResourceByResourceId(xaCmsResource.getResourceId());
			if(xcrrList!=null && xcrrList.size()>0)	xaCmsRoleResourceRepository.delete(xcrrList);
			log.info("假删资源 "+xaCmsResource.getResourceName()+" 信息成功");
			return 1;
		}
		log.info("假删资源 "+rid+" 对应的资源失败，找不到对应的资源");
		return 0;
	}

	@Override
	@Transactional(readOnly=false)
	public XaResult<XaCmsResource> delXaCmsResources(String ids) throws BusinessException {
		XaResult<XaCmsResource> result = new XaResult<XaCmsResource>();
		Collection<Long> idc = new ArrayList<Long>();
		String[] idArray=ids.split(",");
		for(int i = 0;i < idArray.length; i++){
			idc.add(Long.parseLong(idArray[i]));
		}
		List<XaCmsResource> checkList = xaCmsResourceRepository.findExistsParentResoutceList(idc);
		if(checkList != null && checkList.size() > 0){
	        throw new BusinessException("选择的资源存在子资源,请先删除子资源!");
		}
		else{
			for (int i = 0; i < idArray.length; i++) {			
				this.delXaCmsResource(Integer.parseInt(idArray[i]));
				XaSecurityMetadataSourceService.reset();	//刷新security中的资源列表
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false)
	public XaCmsResource updateXaCmsResource(XaCmsResource xaCmsResource)  throws BusinessException {
		XaCmsResource oldResource=xaCmsResourceRepository.findOne(xaCmsResource.getResourceId());
		if(oldResource!=null){
			oldResource.setParentId(xaCmsResource.getParentId());
			oldResource.setResourceName(xaCmsResource.getResourceName());
			oldResource.setResourceUrl(xaCmsResource.getResourceUrl());
			oldResource.setShowType(xaCmsResource.getShowType());
			xaCmsResourceRepository.save(oldResource);
		}
		return oldResource;
	}

	@Override
	public Page<XaCmsResource> findXaCmsResourceByConditon(
			Map<String, Object> filterParams, Pageable pageable)  throws BusinessException{
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		Iterable<XaCmsResource> iterable = xaCmsResourceRepository.findAll();
		Page<XaCmsResource> page = xaCmsResourceRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsResource.class), pageable);
		if(page != null && page.hasContent()){
			for(XaCmsResource xaCmsResource : page.getContent()){
				Iterator<XaCmsResource> iterator = iterable.iterator();
				while(iterator.hasNext()){
					XaCmsResource resource = iterator.next();
					if(xaCmsResource.getParentId()!=null && xaCmsResource.getParentId().longValue() == resource.getResourceId().longValue()){
						xaCmsResource.setParentName(resource.getResourceName());
					}
				}
			}
		}
		return page;
	}

	@Override
	public List<XaCmsResource> getParentResouces(int status,int showType)  throws BusinessException {
		return xaCmsResourceRepository.findParentResourceByStatus(status,showType);
	}

	@Override
	public XaCmsResource findXaCmsResourceById(long rid)  throws BusinessException {
		return xaCmsResourceRepository.findOne(rid);
	}

	@Override
	public List<XaCmsResource> getMenuLevelResource()  throws BusinessException {
		return xaCmsResourceRepository.findResourceByShowTypeAndStatus(XaConstant.ResourceShowType.menu_level, XaConstant.ResourcesStatus.status_normal);
	}
	
	

}

