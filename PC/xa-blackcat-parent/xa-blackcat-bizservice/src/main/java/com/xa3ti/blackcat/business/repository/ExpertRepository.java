package com.xa3ti.blackcat.business.repository;

import com.xa3ti.blackcat.business.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Expert;


public interface ExpertRepository extends
		PagingAndSortingRepository<Expert,  String >,
		JpaSpecificationExecutor<Expert> {
	public Expert findByTidAndStatusNot( String  id,Integer status);

	@Query("from Expert e where e.status=1 and e.name=?1")
	public Expert findExpertByName(String  name);
}
