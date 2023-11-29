package com.devsuperior.uri2602.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2602.dto.CustomerDTO;
import com.devsuperior.uri2602.entities.Customer;
import com.devsuperior.uri2602.projections.CustomerNameProjection;

public interface CustomRepository extends JpaRepository<Customer, Long> {

	@Query(nativeQuery = true, value = "select name from customers c where UPPER(state) = UPPER(:state)")
	List<CustomerNameProjection> name1(String state);
	
	@Query("select new com.devsuperior.uri2602.dto.CustomerDTO(c.name) "
			+ "from Customer c  "
			+ "where UPPER(c.state) = UPPER(:state)")
	List<CustomerDTO> name2(String state);
}
