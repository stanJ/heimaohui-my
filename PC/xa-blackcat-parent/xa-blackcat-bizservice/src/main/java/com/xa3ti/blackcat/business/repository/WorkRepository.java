package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Work;


public interface WorkRepository extends
		PagingAndSortingRepository<Work,  String >,
		JpaSpecificationExecutor<Work> {
	public Work findByTidAndStatusNot( String  id,Integer status);
}
