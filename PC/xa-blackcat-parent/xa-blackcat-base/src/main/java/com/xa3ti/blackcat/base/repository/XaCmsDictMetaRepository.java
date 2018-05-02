/**
 * 
 */
package com.xa3ti.blackcat.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.base.entity.XaCmsDict;
import com.xa3ti.blackcat.base.entity.XaCmsDictMeta;

/**
 * @author nijie
 *
 */
public interface XaCmsDictMetaRepository  extends PagingAndSortingRepository<XaCmsDictMeta, Long>,
JpaSpecificationExecutor<XaCmsDictMeta>{
	
}