package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Log;


public interface LogRepository extends
		PagingAndSortingRepository<Log, Long>,
		JpaSpecificationExecutor<Log> {
	public Log findByTidAndStatusNot(Long id,Integer status);
}
