package com.xa3ti.blackcat.base.service.impl;

import java.util.ArrayList;
import java.util.Collection;
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
import com.xa3ti.blackcat.base.entity.XaCmsRole;
import com.xa3ti.blackcat.base.entity.XaCmsRoleResource;
import com.xa3ti.blackcat.base.entity.XaCmsUser;
import com.xa3ti.blackcat.base.exception.BusinessException;
import com.xa3ti.blackcat.base.repository.XaCmsResourceRepository;
import com.xa3ti.blackcat.base.repository.XaCmsRoleRepository;
import com.xa3ti.blackcat.base.repository.XaCmsRoleResourceRepository;
import com.xa3ti.blackcat.base.repository.XaCmsUserRepository;
import com.xa3ti.blackcat.base.service.XaCmsRoleService;
import com.xa3ti.blackcat.base.util.DateProcessUtil;
import com.xa3ti.blackcat.base.util.DynamicSpecifications;
import com.xa3ti.blackcat.base.util.MyPage;
import com.xa3ti.blackcat.base.util.SearchFilter;
import com.xa3ti.blackcat.base.util.XaUtil;
import com.xa3ti.blackcat.base.vo.NodeStatus;
import com.xa3ti.blackcat.base.vo.TreeNode;

/**
 * @Title: XaCmsRoleServiceImpl.java
 * @Package com.xa3ti.hhrz.business.service.impl
 * @Description: 角色服务类
 * @author hchen
 * @date 2014年8月2日 上午10:25:17
 * @version V1.0
 */
@Service("xaCmsRoleService")
public class XaCmsRoleServiceImpl implements XaCmsRoleService {

	private static final Logger log=Logger.getLogger(XaCmsRoleServiceImpl.class);
	@Autowired
	private XaCmsRoleRepository xaCmsRoleRepository;
	
	@Autowired
	private XaCmsUserRepository xaCmsUserRepository;
	
	@Autowired
	private XaCmsRoleResourceRepository xaCmsRoleResourceRepository;
	
	@Autowired
	private XaCmsResourceRepository xaCmsResourceRepository;
	

	@Override
	@Transactional(readOnly=false)
	public XaCmsRole saveXaCmsRole(XaCmsRole xaCmsRole,String resourceIds) throws BusinessException {
		xaCmsRole.setStatus(XaConstant.RoleStatus.status_normal);
		xaCmsRole =xaCmsRoleRepository.save(xaCmsRole);
		createRoleResource(xaCmsRole.getRoleId(), resourceIds);
		return xaCmsRole;
	}

	/**
	 * @Title: createRoleResource
	 * @Description: 创建角色和资源关系
	 * @param roleId
	 * @param resourceIds    
	 */
	private void createRoleResource(long roleId, String resourceIds) throws BusinessException {
		String[] ids=resourceIds.split(",");
		Collection<Long> idc = new ArrayList<Long>();
		for(int i = 0;i < ids.length; i++){
			idc.add(Long.parseLong(ids[i]));
		}
		List<XaCmsResource> list = xaCmsResourceRepository.findMissParentResource(idc);
		List<Long> idList = new ArrayList<Long>();
		for(XaCmsResource xaCmsResource : list){
			if(!idList.contains(xaCmsResource.getParentId())){
				idList.add(xaCmsResource.getParentId());
				XaCmsRoleResource xaCmsRoleResource=new XaCmsRoleResource();
				xaCmsRoleResource.setRoleId(roleId);
				xaCmsRoleResource.setResourceId(xaCmsResource.getParentId());
				xaCmsRoleResourceRepository.save(xaCmsRoleResource);
			}
		}
		for (int i = 0; i < ids.length; i++) {
			if("0".equals(ids[i])) continue;	//去掉虚拟的根节点
			XaCmsRoleResource xaCmsRoleResource=new XaCmsRoleResource();
			xaCmsRoleResource.setRoleId(roleId);
			xaCmsRoleResource.setResourceId(Long.parseLong(ids[i]));
			xaCmsRoleResourceRepository.save(xaCmsRoleResource);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public boolean delXaCmsRole(String rids) throws BusinessException {
		String[] ids=rids.split(",");
		for (int i = 0; i < ids.length; i++) {	
			long roleId=Long.parseLong(ids[i]);
			XaCmsRole xaCmsRole=xaCmsRoleRepository.findOne(roleId);
			if(xaCmsRole!=null){
				xaCmsRole.setStatus(XaConstant.RoleStatus.status_delete);
				xaCmsRoleRepository.save(xaCmsRole);
				/*List<XaCmsUserRole> urList = xaCmsUserRoleRepository.findXacmsUserRoleByRoleId(roleId);
				if(urList!=null && urList.size()>0) xaCmsUserRoleRepository.delete(urList);
				log.info("删除角色："+xaCmsRole.getRoleName()+" 成功");*/
			}
		}
		return true;
	}

	@Override
	@Transactional(readOnly=false)
	public XaCmsRole updateXaCmsRole(XaCmsRole xaCmsRole,String resourceIds) throws BusinessException {
		XaCmsRole oldXaCmsRole=xaCmsRoleRepository.findOne(xaCmsRole.getRoleId());
		if(oldXaCmsRole!=null){
			oldXaCmsRole.setRoleName(xaCmsRole.getRoleName());
			oldXaCmsRole.setRoleDesc(xaCmsRole.getRoleDesc());
			oldXaCmsRole.setUpdateTime(DateProcessUtil.getToday());
			xaCmsRole =xaCmsRoleRepository.save(oldXaCmsRole);
		}
		if(!XaUtil.isEmpty(resourceIds)){
			List<XaCmsRoleResource> rrList = xaCmsRoleResourceRepository.findRoleResourceByRoleId(xaCmsRole.getRoleId());
			xaCmsRoleResourceRepository.delete(rrList);
			createRoleResource(xaCmsRole.getRoleId(),resourceIds);
		}
		return xaCmsRole;
	}

	@Override
	public Page<XaCmsRole> findXaCmsRoleByConditon(
			Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		Page<XaCmsRole> page = xaCmsRoleRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsRole.class),pageable);
		List<XaCmsRole> list = page.getContent();
		for(XaCmsRole role : list){
			if(role.getUserId() != null){
				XaCmsUser user = xaCmsUserRepository.findOne(role.getUserId());
				if(user != null){
					role.setUserName(user.getNickName());
				}
			}
		}
		int count = xaCmsRoleRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsRole.class)).size();
		MyPage<XaCmsRole> resultPage = new MyPage<XaCmsRole>(pageable.getPageNumber(), pageable.getPageSize(), list, count);
		return resultPage;
	}

	@Override
	public int saveRoleResourceList(long rid, Integer[] resourceIds) {
		return 0;
	}

	@Override
	public List<XaCmsResource> getMyResourceByRoldId(long rid) {
		return null;
	}

	@Override
	public TreeNode getResourceTreeNode(long roleId) throws BusinessException {
		//当roleId为0时，表示新增，新增时，所有的资源都不会被选中。
		List<XaCmsRoleResource>  roleResourceList=xaCmsRoleResourceRepository.findRoleResourceByRoleId(roleId);
		List<Long>  myResourceIdList=new ArrayList<Long>();	//存放我的资源位置
		for (XaCmsRoleResource xaCmsRoleResource : roleResourceList) {
			long resourceId=xaCmsRoleResource.getResourceId();
			if(!myResourceIdList.contains(resourceId)) myResourceIdList.add(resourceId);
		}
		
		
		List<TreeNode> children1=new ArrayList<TreeNode>();	//一级资源
		
		List<XaCmsResource>  pageResourceList=xaCmsResourceRepository.findParentResourceByStatus( XaConstant.ResourcesStatus.status_normal,XaConstant.ResourceShowType.page_level);	//页面级资源
		List<XaCmsResource> rootMenuResourceList=xaCmsResourceRepository.findParentResourceByStatus(XaConstant.ResourcesStatus.status_normal,XaConstant.ResourceShowType.menu_level);	//一级菜单资源
		List<XaCmsResource> firstResourceList=new ArrayList<XaCmsResource>();
		firstResourceList.addAll(pageResourceList);
		firstResourceList.addAll(rootMenuResourceList);
		for (XaCmsResource xaCmsResource : firstResourceList) {
			//做一级菜单中子菜单全选中判断,如果未全选中,则将该1及菜单为非选中,主要是jstree的问题
			boolean firstAllLoad = true;
			List<TreeNode> children2=new ArrayList<TreeNode>();		//菜单级资源
			List<XaCmsResource> secondResourceList=xaCmsResourceRepository.findResourceByParentIdAndStatus(xaCmsResource.getResourceId(), XaConstant.ResourcesStatus.status_normal);	//获取二级菜单
			for (XaCmsResource xaCmsResource2 : secondResourceList) {
				//做二级菜单中子菜单全选中判断,如果未全选中,则将该1及菜单为非选中,主要是jstree的问题
				boolean secendAllLoad = true;
				List<TreeNode> children3=new ArrayList<TreeNode>();		//action级资源
				List<XaCmsResource> thirdResourceList=xaCmsResourceRepository.findResourceByParentIdAndStatus(xaCmsResource2.getResourceId(), XaConstant.ResourcesStatus.status_normal);	//获取三级资源
				for (XaCmsResource xaCmsResource3 : thirdResourceList) {
					NodeStatus thirdNs=new NodeStatus(true,false,false);
					if(myResourceIdList.contains(xaCmsResource3.getResourceId())){
						thirdNs.setSelected(true);
					}
					else{
						firstAllLoad = false;
						secendAllLoad = false;
					}
					String icon3="";
					if(XaConstant.ResourceShowType.page_level==xaCmsResource3.getShowType()){
						icon3=XaConstant.TreeNodeIcon.html_24;
					}else if(XaConstant.ResourceShowType.menu_level==xaCmsResource3.getShowType()){
						icon3=XaConstant.TreeNodeIcon.menu_24;
					}else if(XaConstant.ResourceShowType.button_level==xaCmsResource3.getShowType()){
						icon3=XaConstant.TreeNodeIcon.action_24;
					}
					TreeNode thirdTn=new TreeNode(xaCmsResource3.getResourceId()+"",xaCmsResource3.getResourceName(),thirdNs,icon3,null);
					children3.add(thirdTn);
				}
				
				
				
				//构造二级
				NodeStatus secondNs=new NodeStatus(true,false,false);
				if(myResourceIdList.contains(xaCmsResource2.getResourceId())){
					if(secendAllLoad){
						secondNs.setSelected(true);
					}
					else{
						firstAllLoad = false;
					}
				}
				else{
					firstAllLoad = false;
				}
				String icon2="";
				if(XaConstant.ResourceShowType.page_level==xaCmsResource2.getShowType()){
					icon2=XaConstant.TreeNodeIcon.html_24;
				}else if(XaConstant.ResourceShowType.menu_level==xaCmsResource2.getShowType()){
					icon2=XaConstant.TreeNodeIcon.menu_24;
				}else if(XaConstant.ResourceShowType.button_level==xaCmsResource2.getShowType()){
					icon2=XaConstant.TreeNodeIcon.action_24;
				}
				TreeNode secondTn=new TreeNode(xaCmsResource2.getResourceId()+"",xaCmsResource2.getResourceName(),secondNs,icon2,children3);
				children2.add(secondTn);
			}
			
			
			
			
			NodeStatus firstNs=new NodeStatus(true,false,false);
			if(myResourceIdList.contains(xaCmsResource.getResourceId())&&firstAllLoad) firstNs.setSelected(true);
			String icon1="";
			if(XaConstant.ResourceShowType.page_level==xaCmsResource.getShowType()){
				icon1=XaConstant.TreeNodeIcon.html_24;
			}else if(XaConstant.ResourceShowType.menu_level==xaCmsResource.getShowType()){
				icon1=XaConstant.TreeNodeIcon.menu_24;
			}else if(XaConstant.ResourceShowType.button_level==xaCmsResource.getShowType()){
				icon1=XaConstant.TreeNodeIcon.action_24;
			}
			TreeNode firstTn=new TreeNode(xaCmsResource.getResourceId()+"", xaCmsResource.getResourceName(), firstNs, icon1, children2);
			children1.add(firstTn);
		}
		
		
		//资源树
		NodeStatus rootStatus=new NodeStatus(true,false,false);
		TreeNode root=new TreeNode("0","资源列表",rootStatus,"",children1);
		return root;
	}

	@Override
	public XaCmsRole findRoleById(long roleId) throws BusinessException {
		return xaCmsRoleRepository.findOne(roleId);
	}

	@Override
	public XaCmsRole getRoleByName(String roleName) throws BusinessException {
		
		return xaCmsRoleRepository.findRoleByRoleName(roleName);
	}
	
	

}

