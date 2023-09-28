package com.devsuperior.dscommerce.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatusCode;

public class ProductControllerRA {

	private Long existProductId, nonExistingProductId;
	
	@BeforeEach
	void setup() {
		baseURI = "http://localhost:8080";
	}
	
	@Test
	public void findByIdshoukdReturnProductWhenIdExists() {
		existProductId = 2L;
		
		given()
			.get("/product/{id}", 2)
		.then()
			.statusCode(200)
			.body("id", is(existProductId))
			.body("name", equalTo("Smart TV"))
			.body("price", is(2190.0F))
			.body("imgUrl", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg"))
			.body("categories.id", hasItems(2, 3))
			.body("categories.name", hasItems("Eletrônicos", "Computadores"));
	}
}
