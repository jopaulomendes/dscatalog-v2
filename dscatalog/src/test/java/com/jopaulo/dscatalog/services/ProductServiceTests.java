package com.jopaulo.dscatalog.services;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.jopaulo.dscatalog.repositories.ProductRepository;
import com.jopaulo.dscatalog.services.exceptions.ResourceNotFoundException;

public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependenceID;
	
	@BeforeEach
	void serUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependenceID = 3L;
		
//		Mockito.doNothing().when(repository).deleteById(existingId);
		
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependenceID)).thenReturn(true);

	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		Mockito.verify(repository, times(1)).deleteById(existingId);
	}
}
