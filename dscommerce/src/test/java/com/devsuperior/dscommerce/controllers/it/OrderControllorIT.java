package com.devsuperior.dscommerce.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.tests.ProductFactory;
import com.devsuperior.dscommerce.tests.TokenUtil;
import com.devsuperior.dscommerce.tests.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllorIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private ObjectMapper objectMapper;

	private Long existisOrderId, nonExistisOrdertId;
	private String adminToken, clientToken, invalidToken;
	private String adminUsername, clientUsername, adminPassword, clientePassword;

	private Order order;
	private OrderDTO orderDTO;
	private User user;
	
	private Product product;
	
	@BeforeEach
	void setup() throws Exception {
		adminUsername = "alex@gmail.com";
		clientUsername = "maria@gmail.com";
		adminPassword = "123456";
		clientePassword = "123456";
		
		existisOrderId = 1L;
		nonExistisOrdertId = 1000L;
		
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientePassword);
		invalidToken = adminToken + "xpto";
		
		user = UserFactory.createClientUser();
		order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, user, null);
		
		product = ProductFactory.createProduct();
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndAminLogged() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", existisOrderId)
						.header("Authorization", "Bearer " + adminToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existisOrderId));
		result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
		result.andExpect(jsonPath("$.status").value("PAID"));
		result.andExpect(jsonPath("$.client").exists());
		result.andExpect(jsonPath("$.client.name").value("Maria Brown"));
		result.andExpect(jsonPath("$.payment").exists());
		result.andExpect(jsonPath("$.items").exists());
		result.andExpect(jsonPath("$.items[1].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.total").exists());
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndClientLogged() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", existisOrderId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existisOrderId));
		result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
		result.andExpect(jsonPath("$.status").value("PAID"));
		result.andExpect(jsonPath("$.client").exists());
		result.andExpect(jsonPath("$.client.name").value("Maria Brown"));
		result.andExpect(jsonPath("$.payment").exists());
		result.andExpect(jsonPath("$.items").exists());
		result.andExpect(jsonPath("$.items[1].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.total").exists());
	}
	
	@Test
	public void findByIdShouldReturnForbidenWhenIdExistsAndClientLoggedAndOrderDoesNotBelongUser() throws Exception {
		Long otheOrderId = 2L;
		
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", otheOrderId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsWhenAdminLogged() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", nonExistisOrdertId)
						.header("Authorization", "Bearer " + adminToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsWhenClientLogged() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", nonExistisOrdertId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnUnonauthorizeWhenIdExistsWhenAdminLoggedAndInvalidToken() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", existisOrderId)
						.header("Authorization", "Bearer " + invalidToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isUnauthorized());
	}
}
