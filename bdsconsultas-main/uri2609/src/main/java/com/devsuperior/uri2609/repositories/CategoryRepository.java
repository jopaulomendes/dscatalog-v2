package com.devsuperior.uri2609.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2609.dto.CategorySumDTO;
import com.devsuperior.uri2609.entities.Category;
import com.devsuperior.uri2609.projections.CategorySumProjection;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(nativeQuery = true, value = "select c.name, sum(p.amount) "
			+ "from categories c "
			+ "inner join products p on p.id_categories  = c.id  "
			+ "group by c.name")
	List<CategorySumProjection> busca1();
	
	@Query("select new com.devsuperior.uri2609.dto.CategorySumDTO(p.category.name, sum(p.amount)) "
			+ "from Product p "
			+ "group by p.category.name")
	List<CategorySumDTO> busca2();
}
