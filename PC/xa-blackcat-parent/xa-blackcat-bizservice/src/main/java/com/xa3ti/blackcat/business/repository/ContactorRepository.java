package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Contactor;


public interface ContactorRepository extends
		PagingAndSortingRepository<Contactor,  String >,
		JpaSpecificationExecutor<Contactor> {
	public Contactor findByTidAndStatusNot( String  id,Integer status);
}
