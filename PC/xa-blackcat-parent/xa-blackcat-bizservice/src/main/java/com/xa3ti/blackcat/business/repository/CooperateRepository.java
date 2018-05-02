package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Cooperate;


public interface CooperateRepository extends
		PagingAndSortingRepository<Cooperate,  String >,
		JpaSpecificationExecutor<Cooperate> {
	public Cooperate findByTidAndStatusNot( String  id,Integer status);
}
