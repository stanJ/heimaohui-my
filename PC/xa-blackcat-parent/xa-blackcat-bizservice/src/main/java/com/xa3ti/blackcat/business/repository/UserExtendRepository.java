package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.UserExtend;


public interface UserExtendRepository extends
		PagingAndSortingRepository<UserExtend,  String >,
		JpaSpecificationExecutor<UserExtend> {
	public UserExtend findByTidAndStatusNot( String  id,Integer status);
	
	@Query("from UserExtend ue where ue.status=1 and ue.userId=?1")
	public UserExtend findUserExtendByUserId(String userId);
}
