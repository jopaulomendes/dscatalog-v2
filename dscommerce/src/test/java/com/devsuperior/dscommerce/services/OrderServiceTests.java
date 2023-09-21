package com.devsuperior.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.tests.OrderFactory;
import com.devsuperior.dscommerce.tests.ProductFactory;
import com.devsuperior.dscommerce.tests.UserFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	@InjectMocks
	private OrderService service;

	@Mock
	private OrderRepository repository;

	@Mock
	private AuthService authService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private OrderItemRepository orderItemRepository;

	@Mock
	private UserService userService;

	private Long existngOrderId, nonExisitngOrderId, existingProductId, nonExistingProductId;
	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;
	private Product product;

	@BeforeEach
	void setUp() throws Exception {
		existngOrderId = 1L;
		nonExisitngOrderId = 2L;
		
		existingProductId = 1L;
		nonExistingProductId = 2L;

		admin = UserFactory.createCustomAdminUser(1L, "Juca Bala");
		client = UserFactory.createCustomAdminUser(2L, "Mucurinha");

		order = OrderFactory.createOrder(client);
		orderDTO = new OrderDTO(order);
		
		product = ProductFactory.createProduct();

		Mockito.when(repository.findById(existngOrderId)).thenReturn(Optional.of(order));
		Mockito.when(repository.findById(nonExisitngOrderId)).thenReturn(Optional.empty());

		Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
		Mockito.when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(repository.save(any())).thenReturn(order);
		
		Mockito.when(orderItemRepository.saveAll(any())).thenReturn(new ArrayList<>(order.getItems()));
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
	
	@Test
	public void insertShouldReturnOrderDTOWhenAdminLogged() {
		Mockito.when(userService.authenticated()).thenReturn(admin);

		order.setClient(new User());
		orderDTO = new OrderDTO(order);

		Assertions.assertNotNull(orderDTO);
		Assertions.assertEquals(orderDTO.getId(), existngOrderId);
	}
	
	@Test
	public void insertShouldReturnOrderDTOWhenClientLogged() {
		Mockito.when(userService.authenticated()).thenReturn(client);
		
		OrderDTO result = service.insert(orderDTO);

		Assertions.assertNotNull(result);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void insertShouldThrosWhenUsernameResourceNotFoundExceptionWhenUserNotLogged() {
		Mockito.doThrow(UsernameNotFoundException.class).when(userService).authenticated();
		
		order.setClient(new User());
		orderDTO = new OrderDTO(order);

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			OrderDTO result = service.insert(orderDTO);
		});
	}
	
	@SuppressWarnings("unused")
	@Test
	public void insertShouldThrosEntityNotFoundExceptionWhenOrderProductIdDoesNortExists() {
		Mockito.when(userService.authenticated()).thenReturn(client);

		product.setId(nonExistingProductId);
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
		
		orderDTO = new OrderDTO(order);
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			OrderDTO result = service.insert(orderDTO);
		});
	}
}
