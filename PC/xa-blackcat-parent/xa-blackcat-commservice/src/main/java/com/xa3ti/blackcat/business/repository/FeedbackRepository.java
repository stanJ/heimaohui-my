package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Feedback;


public interface FeedbackRepository extends
		PagingAndSortingRepository<Feedback, Long>,
		JpaSpecificationExecutor<Feedback> {
	public Feedback findByTidAndStatusNot(Long id,Integer status);
}
