package com.jopaulo.dscatalog.repostories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jopaulo.dscatalog.entities.Product;
import com.jopaulo.dscatalog.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		long exitingId = 1L;
		
		repository.deleteById(exitingId);
		
		Optional<Product> result = repository.findById(exitingId);
		Assertions.assertFalse(result.isPresent());
	}
}
