package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Assistant;


public interface AssistantRepository extends
		PagingAndSortingRepository<Assistant,  String >,
		JpaSpecificationExecutor<Assistant> {
	public Assistant findByTidAndStatusNot( String  id,Integer status);
}
