package com.xa3ti.blackcat.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.base.entity.XaCmsUser;

public interface XaCmsUserRepository extends
		PagingAndSortingRepository<XaCmsUser, String>,
		JpaSpecificationExecutor<XaCmsUser> {

	@Query("from XaCmsUser xcu where xcu.userName=?1 and xcu.status=?2")
	public XaCmsUser findByUserName(String userName,int status);
	
	public List<XaCmsUser> findByUserName(String userName);
}
