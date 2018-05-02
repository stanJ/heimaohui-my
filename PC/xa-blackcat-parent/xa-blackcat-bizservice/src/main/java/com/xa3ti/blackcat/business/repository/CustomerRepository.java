package com.xa3ti.blackcat.business.repository;

import com.xa3ti.blackcat.business.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CustomerRepository extends
		PagingAndSortingRepository<Customer,  String >,
		JpaSpecificationExecutor<Customer> {
	public Customer findByTidAndStatusNot( String  id,Integer status);

    @Query("from Customer c where c.status=1 and c.name=?1")
	public Customer findCustomerByName( String  name);
}
