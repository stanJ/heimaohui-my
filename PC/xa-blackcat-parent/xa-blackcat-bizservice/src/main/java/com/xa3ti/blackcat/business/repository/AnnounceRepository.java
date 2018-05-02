package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Announce;


public interface AnnounceRepository extends
		PagingAndSortingRepository<Announce,  String >,
		JpaSpecificationExecutor<Announce> {
	public Announce findByTidAndStatusNot( String  id,Integer status);
}
