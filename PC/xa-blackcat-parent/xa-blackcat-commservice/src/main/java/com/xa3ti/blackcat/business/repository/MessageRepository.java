package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Message;


public interface MessageRepository extends
		PagingAndSortingRepository<Message, Long>,
		JpaSpecificationExecutor<Message> {
	public Message findByTidAndStatusNot(Long id,Integer status);
}
