package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.ExpertCategory;


public interface ExpertCategoryRepository extends
		PagingAndSortingRepository<ExpertCategory,  String >,
		JpaSpecificationExecutor<ExpertCategory> {
	public ExpertCategory findByTidAndStatusNot( String  id,Integer status);
}
