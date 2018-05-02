package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.News;


public interface NewsRepository extends
		PagingAndSortingRepository<News,  String >,
		JpaSpecificationExecutor<News> {
	public News findByTidAndStatusNot( String  id,Integer status);
	@Query("from News b where tid=?1 ")
	public News findByTidAndStatusNot( String  id);
}
