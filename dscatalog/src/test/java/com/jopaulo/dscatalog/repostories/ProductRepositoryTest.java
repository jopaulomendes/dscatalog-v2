package com.jopaulo.dscatalog.repostories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jopaulo.dscatalog.entities.Product;
import com.jopaulo.dscatalog.repositories.ProductRepository;
import com.jopaulo.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTest {
	
	private long exitingId;
	private long noExitingId;
	private long countTotalProducts;
	
	@Autowired
	private ProductRepository repository;
	
	@BeforeEach
	void serUp() throws Exception {
		exitingId = 1L;
		noExitingId = 100L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistewithAutoIncrementWhenIdIssNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(exitingId);
		
		Optional<Product> result = repository.findById(exitingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void findByIdShoulReturnNonEmptyoptionalWhenIdExists() {
		Optional<Product> result =  repository.findById(exitingId);
		Assertions.assertTrue(result.isPresent());
		
	}
	
	@Test
	public void findByIdShoulReturnEmptyoptionalWhenIdNotExists() {
		Optional<Product> result =  repository.findById(noExitingId);
		Assertions.assertTrue(result.isEmpty());
		
	}
	
	
	
	
	
}
