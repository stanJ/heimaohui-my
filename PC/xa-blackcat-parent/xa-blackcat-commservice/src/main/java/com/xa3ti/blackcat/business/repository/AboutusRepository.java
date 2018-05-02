package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Aboutus;


public interface AboutusRepository extends
		PagingAndSortingRepository<Aboutus, Long>,
		JpaSpecificationExecutor<Aboutus> {
	public Aboutus findByTidAndStatusNot(Long id,Integer status);
}
