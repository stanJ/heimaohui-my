/**
 * 
 */
package com.xa3ti.blackcat.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.base.entity.XaCmsDict;
import com.xa3ti.blackcat.base.entity.XaCmsResource;
import com.xa3ti.blackcat.base.entity.XaCmsRole;

/**
 * @author nijie
 *
 */
public interface XaCmsDictRepository extends PagingAndSortingRepository<XaCmsDict, Long>,
JpaSpecificationExecutor<XaCmsDict>{

	
	/**
	 * @Title: findAllXaCmsRole
	 * @Description: 根据角色状态查询角色集合
	 * @param status 1表示正常，0表示删除
	 * @return    
	 */
	@Query("select distinct dict.key from XaCmsDict dict where dict.status=?1")
	List<String>  findAllKeys(int status);
	

}
