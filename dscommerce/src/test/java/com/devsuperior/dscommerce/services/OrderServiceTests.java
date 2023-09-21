package com.devsuperior.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.tests.OrderFactory;
import com.devsuperior.dscommerce.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	@InjectMocks
	private OrderService service;
	
	@Mock
	 private OrderRepository repository;
	
	@Mock
	private AuthService authService;

//	@Mock
//	private ProductRepository productRepository;
//
//	@Mock
//	private UserService userService;
	
	private Long existngOrderId, nonExisitngOrderId;
	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;

	@BeforeEach
	void setUp() throws Exception {
		existngOrderId = 1L;
		nonExisitngOrderId = 2L;
		
		admin = UserFactory.createCustomAdminUser(1L, "Juca Bala");
		client = UserFactory.createCustomAdminUser(2L, "Mucurinha");
		
		order = OrderFactory.createOrder(client);
		orderDTO = new OrderDTO(order);
		
		Mockito.when(repository.findById(existngOrderId)).thenReturn(Optional.of(order));
		Mockito.when(repository.findById(nonExisitngOrderId)).thenReturn(Optional.empty());
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogger() {
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());
		
		OrderDTO result = service.findById(existngOrderId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existngOrderId);
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndSelfClientLogger() {
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());
		
		OrderDTO result = service.findById(existngOrderId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existngOrderId);
	}
	

	@SuppressWarnings("unused")
	@Test
	public void findByIdShouldthrowsForbiddenExceptionWhenIdExistsAndOtherClientLogged() {
		Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());
		
		Assertions.assertThrows(ForbiddenException.class, () -> {
			OrderDTO result = service.findById(existngOrderId);
		});
	}
	
	@SuppressWarnings("unused")
	@Test
	public void findByIdShouldthrowsResourceNotFoundExceptionWhenIdDoesNotExist() {
Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			OrderDTO result = service.findById(nonExisitngOrderId);
		});
	}
}
