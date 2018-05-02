/**
 *  Copyright 2016 3ti Corp. Limited
 */
package com.xa3ti.blackcat.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xa3ti.blackcat.business.entity.Constant;





/**
 * @author nijie
 *
 */
public  interface ConstantRepository
		extends PagingAndSortingRepository<Constant, String>,
		JpaSpecificationExecutor<Constant> {

	public  Constant findByTidAndStatusNot(String id, Integer status);
	
	
	@Query("from Constant c where c.status=1 and c.constantKey=?1")
	public abstract Constant findConstantByKey(String key);
}
