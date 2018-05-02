package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Pics;


public interface PicsRepository extends
		PagingAndSortingRepository<Pics,  String >,
		JpaSpecificationExecutor<Pics> {
	public Pics findByTidAndStatusNot( String  id,Integer status);
}
