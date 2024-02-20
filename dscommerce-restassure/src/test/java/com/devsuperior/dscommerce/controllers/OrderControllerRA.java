package com.devsuperior.dscommerce.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dscommerce.tests.TokenUtil;

import io.restassured.http.ContentType;

public class OrderControllerRA {

	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	private Long existOrderId, nonExistingOrderId;
	
	@BeforeEach
	void setup() {
		baseURI = "http://localhost:8080";
		
		existOrderId = 2L;
		nonExistingOrderId = 100L;
		
		adminUsername = "alex@gmail.com";
		adminPassword = "123456";
		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		
		adminToken = TokenUtil.accessToken(adminUsername, adminPassword);
		clientToken = TokenUtil.accessToken(clientUsername, clientPassword);
		invalidToken = adminToken + "xpto";
	}
	
	@Test
	public void findByIdshoukdReturnOrdertWhenIdExistsAndAdminLogged() {
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.accept(ContentType.JSON)
		.when()
			.get("/orders/{id}", existOrderId)
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("moment", equalTo("2022-07-25T13:00:00Z"))
			.body("status", equalTo("PAID"))
			.body("client.name", equalTo("Maria Brown"))
			.body("payment.moment",equalTo("2022-07-25T15:00:00Z"))
			.body("itens.name", hasItems("The Lord of the Rings", "Macbook Pro"))
			.body("total", is(1431.0F));
	}
	
	@Test
	public void findByIdshoukdReturnOrdertWhenIdExistsAndClientLogged() {
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.accept(ContentType.JSON)
		.when()
			.get("/orders/{id}", existOrderId)
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("moment", equalTo("2022-07-25T13:00:00Z"))
			.body("status", equalTo("PAID"))
			.body("client.name", equalTo("Maria Brown"))
			.body("payment.moment",equalTo("2022-07-25T15:00:00Z"))
			.body("itens.name", hasItems("The Lord of the Rings", "Macbook Pro"))
			.body("total", is(1431.0F));
	}
	
	@Test
	public void findByIdshoukdReturnForbidentWhenIdExistsAndClientLoggedAndOrderDoesNotBelongUser() {
		Long otherOrderId = 2L;
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.accept(ContentType.JSON)
		.when()
			.get("/orders/{id}", otherOrderId)
		.then()
			.statusCode(403);
	}
	
	@Test
	public void findByIdshoukdReturnNotFoundWhenIdDeosNotExistsAndAdminLogged() {
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.accept(ContentType.JSON)
		.when()
			.get("/orders/{id}", nonExistingOrderId)
		.then()
			.statusCode(404);
	}
	
	@Test
	public void findByIdshoukdReturnNotFoundWhenIdDeosNotExistsAndClientLogged() {
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.accept(ContentType.JSON)
		.when()
			.get("/orders/{id}", nonExistingOrderId)
		.then()
			.statusCode(404);
	}
	
	@Test
	public void findByIdshoukdReturnUnauthorizeWhenIdExistsAndInvalidTokken() {
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + invalidToken)
			.accept(ContentType.JSON)
		.when()
			.get("/orders/{id}", existOrderId)
		.then()
			.statusCode(401);
	}
}
