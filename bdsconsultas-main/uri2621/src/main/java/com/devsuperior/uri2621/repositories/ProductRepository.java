package com.devsuperior.uri2621.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2621.dto.ProductDTO;
import com.devsuperior.uri2621.entities.Product;
import com.devsuperior.uri2621.projections.ProductNameProjetion;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(nativeQuery = true, value = "select p.name "
			+ "from products p "
			+ "inner join providers p2 on p.id_providers  = p2.id "
			+ "where p.amount between :min and :max "
			+ "and p2.name like upper(concat(:beginName, '%'))")
	List<ProductNameProjetion> busca1(String beginName, Integer min, Integer max);
	
	@Query("select new com.devsuperior.uri2621.dto.ProductDTO(p.name) "
			+ "from Product p "
			+ "where p.amount between :min and :max "
			+ "and p.provider.name like upper(concat(:beginName, '%'))")
	List<ProductDTO> busca2 (String beginName, Integer min, Integer max);
}
