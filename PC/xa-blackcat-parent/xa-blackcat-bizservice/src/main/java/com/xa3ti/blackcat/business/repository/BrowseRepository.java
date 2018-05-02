package com.xa3ti.blackcat.business.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Browse;
import org.springframework.data.repository.query.Param;


public interface BrowseRepository extends
		PagingAndSortingRepository<Browse,  String >,
		JpaSpecificationExecutor<Browse> {
	public Browse findByTidAndStatusNot( String  id,Integer status);
	
	
	
	@Query(value="select count(a.user_id) from (select user_id,expert_id,btype,count(*) from tb_xa_browse b where b.status=1 and b.user_id=?1 and b.btime>=?2 and b.btime<?3 and b.btype=?4 group by user_id,expert_id,btype) a",nativeQuery=true)
	public Integer findBrowseByUserIdDate(String uid,Date startDate,Date endDate,Integer btype);
	
	
	@Query("from Browse b where b.status=1 and b.userId=?1 and b.btime>=?2 and b.btime<?3 and b.btype=?4 and b.expertId=?5")
	public List<Browse> findBrowseByUserId(String uid,Date startDate,Date endDate,Integer btype,String modelId);




}
