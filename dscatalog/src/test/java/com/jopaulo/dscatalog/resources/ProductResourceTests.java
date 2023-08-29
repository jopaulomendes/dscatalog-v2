package com.jopaulo.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jopaulo.dscatalog.dto.ProductDTO;
import com.jopaulo.dscatalog.services.ProductService;
import com.jopaulo.dscatalog.tests.Factory;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private ProductDTO productDTO;
	private PageImpl<ProductDTO> page;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		productDTO = Factory.createProductDto();
		page = new PageImpl<>(List.of(productDTO));
		
		when(service.findAllPage(any())).thenReturn(page);

//		when(service.findById(existingId)).thenReturn(productDTO);
//		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
//
//		when(service.insert(any())).thenReturn(productDTO);
//		
//		when(service.update(eq(existingId), any())).thenReturn(productDTO);
//		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
//		
//		doNothing().when(service).delete(existingId);
//		doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
//		doThrow(DatabaseException.class).when(service).delete(dependentId);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk());
//		ResultActions result = 
//				mockMvc.perform(get("/products")
//					.accept(MediaType.APPLICATION_JSON));
//		result.andExpect(status().isOk());
	}
}
