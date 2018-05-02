package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Intro;


public interface IntroRepository extends
		PagingAndSortingRepository<Intro,  String >,
		JpaSpecificationExecutor<Intro> {
	public Intro findByTidAndStatusNot( String  id,Integer status);
	@Query("from Intro b where tid=?1 ")
	public Intro findByTidAndStatusNot( String  id);
}
