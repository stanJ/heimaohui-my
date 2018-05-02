package com.xa3ti.blackcat.base.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.vo.MyData;

public interface XaCmsResourceRepository extends
		PagingAndSortingRepository<XaCmsResource, Long>,
		JpaSpecificationExecutor<XaCmsResource> {

	@Query("select ro.roleName from XaCmsRoleResource rr,XaCmsRole ro where rr.resourceId = ?1 and rr.roleId=ro.roleId")
	public List<String> findRoleNameByResourceId(long resourceId);

	@Query("select mr.resourceUrl from XaCmsRoleResource rr, XaCmsResource mr"
			+ " where rr.roleId=?1 and rr.resourceId=mr.resourceId")
	public List<String> findResourceByRoleId(Long roleId);
	
	public List<XaCmsResource> findByResourceIdInAndParentIdGreaterThan(Collection<Long> ids,Long parentId);
	
	@Query("from XaCmsResource xcr where xcr.resourceId in ?1 and exists (select 1 from XaCmsResource xcr1 where xcr.resourceId=xcr1.parentId and xcr1.status=1)")
	public List<XaCmsResource> findExistsParentResoutceList(Collection<Long> ids);
	/**
	 * @Title: findParentResourceByStatus
	 * @Description: 根据状态查询一级资源,页面级资源和菜单级资源(parentId为空)
	 * @param status 1表示正常，0表示已删除或禁用
	 * @return    
	 */
	@Query("from XaCmsResource xcr where xcr.parentId is null and xcr.status=?1 and xcr.showType=?2 order by orderNum,resourceId")
	public List<XaCmsResource> findParentResourceByStatus(int status,int showType);
	
	@Query("from XaCmsResource xcr where xcr.resourceId in ?1 and xcr.status=1 and xcr.parentId is not null and xcr.parentId not in ?1")
	public List<XaCmsResource> findMissParentResource(Collection<Long> ids);
	
	/**
	 * @Title: findResourceByShowTypeAndStatus
	 * @Description: 根据showType和status查询资源
	 * @param showType 资源类型
	 * @param status 资源状态
	 * @return    
	 */
	@Query("from XaCmsResource xcr where xcr.showType=?1 and xcr.status=?2")
	public List<XaCmsResource> findResourceByShowTypeAndStatus(int showType,int status);
	
	/**
	 * @Title: findResourceByParentIdAndStatus
	 * @Description: 根据parentId和状态查询2级菜单资源资源
	 * @param parentId
	 * @param status
	 * @return    
	 */
	@Query("from XaCmsResource xcr where xcr.parentId =?1 and xcr.status=?2 order by orderNum,resourceId")
	public List<XaCmsResource> findResourceByParentIdAndStatus(long parentId,int status);
	
	@Query(value = "select t from XaCmsResource t where t.parentId is null and t.status=?1",
	        countQuery = "select count(distinct t ) from XaCmsResource t where t.parentId is null and t.status=?1")
	public Page<XaCmsResource> myFind(int status,Pageable p);
	
}
