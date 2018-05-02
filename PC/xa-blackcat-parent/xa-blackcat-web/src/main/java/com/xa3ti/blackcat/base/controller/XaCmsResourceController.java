package com.xa3ti.blackcat.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xa3ti.blackcat.base.constant.XaConstant;
import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.entity.XaResult;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.security.XaSecurityMetadataSourceService;
import com.xa3ti.blackcat.base.service.XaCmsResourceService;
import com.xa3ti.blackcat.base.util.WebUitl;
import com.xa3ti.blackcat.base.vo.SelectOptionVo;

/**
 * @Title: XaCmsResourceController.java
 * @Package com.xa3ti.hhrz.business.controller
 * @Description: 资源控制器
 * @author hchen
 * @date 2014年8月2日 上午11:42:16
 * @version V1.0
 */
@Controller
@RequestMapping("xaCmsResource")
public class XaCmsResourceController extends BaseController {
	
	@Autowired
	private XaCmsResourceService xaCmsResourceService;

	/**
	 * @Title: getAllResource
	 * @Description: 列表查询所有资源
	 * @param request	
	 * @param response
	 * @param nextPage	下一次，页数从0开始
	 * @param sortDate	排序规则
	 * @param jsonFilter	查询过滤条件
	 * @return    
	 */
	@ResponseBody
	@RequestMapping(value="showResource/{nextPage}/{sortDate}/{jsonFilter}")
	public Page<XaCmsResource> getAllResource(HttpServletRequest request,HttpServletResponse response,
			@PathVariable Integer nextPage,
			@PathVariable String sortDate,
			@PathVariable String jsonFilter) throws BusinessException{
		Pageable pageable = WebUitl.buildPageRequest(nextPage, 10, sortDate);
		Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		filterParams.put("EQ_status", XaConstant.ResourcesStatus.status_normal);
		Page<XaCmsResource> data=xaCmsResourceService.findXaCmsResourceByConditon(filterParams, pageable);
		return data;
	}
	
	/**
	 * @Title: saveResource
	 * @Description: 保存资源
	 * @param request
	 * @param response
	 * @param xaCmsResource
	 * @return    
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public String saveResource(@RequestBody XaCmsResource xaCmsResource) throws BusinessException{
		xaCmsResource.setOrderNum(100);			//设置默认排序
		xaCmsResource.setStatus(XaConstant.ResourcesStatus.status_normal);
		xaCmsResource= xaCmsResourceService.saveXaCmsResource(xaCmsResource);
		XaSecurityMetadataSourceService.reset();	//刷新security中的资源列表
		return "{\"status\":1,\"result\":\"新建资源成功\"}";
	}
	
	/**
	 * @Title: getParentResource
	 * @Description: 返回一级资源
	 * @return    
	 */
	@ResponseBody
	@RequestMapping(value="showParent/{rid}",method=RequestMethod.GET)
	public SelectOptionVo getParentResource(@PathVariable Long rid) throws BusinessException{
		List<XaCmsResource> result=xaCmsResourceService.getMenuLevelResource();
		SelectOptionVo sov=new SelectOptionVo();
		sov.setOptionItem(result);
		if(rid!=null){
			XaCmsResource resource=xaCmsResourceService.findXaCmsResourceById(rid);
			sov.setSelectedId(resource);
		}else{
			sov.setSelectedId("");
		}
		return sov;
	}
	
	/**
	 * @Title: batchDelResource
	 * @Description: 批量删除资源信息，假删资源。注意，删除资源时，需要将角色和资源之间的关系同时删掉
	 * @param ids
	 * @param request
	 * @param response
	 * @return    
	 */
	@ResponseBody
	@RequestMapping(value="resource/{ids}",method=RequestMethod.DELETE)
	public XaResult<XaCmsResource> batchDelResource(
			@PathVariable String ids,HttpServletRequest request,HttpServletResponse response)
			 throws BusinessException{
		@SuppressWarnings("unchecked")
		Map<String, String> map = JSON.parseObject(ids,HashMap.class);
		String ids1= map.get("ids");
		return xaCmsResourceService.delXaCmsResources(ids1);
	}
	
	/**
	 * @Title: updateResource
	 * @Description: 更新一条资源记录
	 * @param id
	 * @param request
	 * @param response
	 * @return    
	 */
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.PUT)
	public String updateResource(@RequestBody XaCmsResource xaCmsResource,HttpServletRequest request,HttpServletResponse response) throws BusinessException{
		XaCmsResource oldResource=xaCmsResourceService.findXaCmsResourceById(xaCmsResource.getResourceId());
		String result="";
		if(oldResource!=null){
			xaCmsResourceService.updateXaCmsResource(xaCmsResource);
			XaSecurityMetadataSourceService.reset();	//刷新security中的资源列表
			result="{\"status\":1,\"result\":\"修改资源成功\"}";
		}else{
			result="{\"status\":0,\"result\":\"修改资源不成功，"+xaCmsResource.getResourceId()+"对应的资源不存在\"}";
		}
		return result;
	}
}

