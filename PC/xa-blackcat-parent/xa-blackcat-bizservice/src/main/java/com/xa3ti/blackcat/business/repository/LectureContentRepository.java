package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.LectureContent;


public interface LectureContentRepository extends
		PagingAndSortingRepository<LectureContent,  String >,
		JpaSpecificationExecutor<LectureContent> {
	public LectureContent findByTidAndStatusNot( String  id,Integer status);
}
