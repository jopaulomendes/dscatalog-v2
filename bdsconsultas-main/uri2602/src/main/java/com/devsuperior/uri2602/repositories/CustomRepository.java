package com.devsuperior.uri2602.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2602.entities.Customer;
import com.devsuperior.uri2602.projections.CustomerNameProjection;

public interface CustomRepository extends JpaRepository<Customer, Long> {

	@Query(nativeQuery = true, value = "select name from customers c where state = :state")
	List<CustomerNameProjection> name(String state);
}
