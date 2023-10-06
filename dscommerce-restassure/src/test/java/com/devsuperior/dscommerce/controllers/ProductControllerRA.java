package com.devsuperior.dscommerce.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dscommerce.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ProductControllerRA {

	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	private Long existProductId, nonExistingProductId, dependentProductId;
	private String productName;
	
	private Map<String, Object> postProductInstance;
	List<Map<String, Object>> categories;
	
	Map<String, Object> category1;
	Map<String, Object> category2;
	
	@BeforeEach
	void setup() {
		baseURI = "http://localhost:8080";
		
		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		adminUsername = "alex@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.accessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.accessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
		
		productName = "Macbook";
		
		postProductInstance = new HashMap<>();
		postProductInstance.put("name", "Meu produto");
		postProductInstance.put("description", "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Qui ad, adipisci illum ipsam velit et odit eaque reprehenderit ex maxime delectus dolore labore, quisquam quae tempora natus esse aliquam veniam doloremque quam minima culpa alias maiores commodi. Perferendis enim");
		postProductInstance.put("imgUrl", "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg");
		postProductInstance.put("price", 50.0);
		
		categories = new ArrayList<>();
		
		category1 = new HashMap<>();
		category1.put("id", 2);
		
		category2 = new HashMap<>();
		category2.put("id", 3);
		
		categories.add(category1);
		categories.add(category2);
		
		postProductInstance.put("categories", categories);
	}
	
	@Test
	public void findByIdshoukdReturnProductWhenIdExists() {
		existProductId = 2L;
		
		given()
			.get("/products/{id}", existProductId)
		.then()
			.statusCode(200)
			.body("id", is(2))
			.body("name", equalTo("Smart TV"))
			.body("price", is(2190.0F))
			.body("imgUrl", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg"))
			.body("categories.id", hasItems(2, 3))
			.body("categories.name", hasItems("Eletrônicos", "Computadores"));
	}
	
	@Test
	public void findAllShoultReturnPageProductsWhenProductNameIsEmpty() {
		given()
			.get("/products?page=0")
		.then()
			.statusCode(200)
			.body("content.name", hasItems("Macbook Pro", "PC Gamer Tera"));
	}
	
	@Test
	public void findAllShoultReturnPageProductsWhenProductNameIsNotEmpty() {
		given()
			.get("/products?name={productName}", productName)
		.then()
			.statusCode(200)
			.body("content.id[0]", is(3))
			.body("content.name[0]", equalTo("Macbook Pro"))
			.body("content.price[0]", is(1250.0F))
			.body("content.imgUrl[0]", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
	}
	
	@Test
	public void findAllShoultReturnPageProductsWhithPriceGreaterThan200() {
		given()
			.get("/products?size=25")
		.then()
			.statusCode(200)
			.body("content.findAll {it.price > 2000}.name", hasItems("Smart TV", "PC Gamer Hera"));
	}
	
	@Test
	public void insertShoultReturnProductCreatedWhenAdminLogged() {
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Beare " + adminToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(201)
			.body("name", equalTo("Meu produto"))
			.body("price", is(50F))
			.body("imgUrl", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"))
			.body("categories.id", hasItems(2, 3));
	}
	
	@Test
	public void insertShoultReturnUnprocessableEntityWhenAdminLoggeAndInvalidName() {
		postProductInstance.put("name", "ab");
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(422)
			.body("errors.message[0]", equalTo("Nome precisar ter de 3 a 80 caracteres"));
	}
	
	@Test
	public void insertShoultReturnUnprocessableEntityWhenAdminLoggeAndInvalidDescription() {
		postProductInstance.put("description", "ab");
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(422)
			.body("errors.message[0]", equalTo("Descrição precisa ter no mínimo 10 caracteres"));
	}
	
	@Test
	public void insertShoultReturnUnprocessableEntityWhenAdminLoggeAndPriceIsNegative() {
		postProductInstance.put("price", -50);
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(422)
			.body("errors.message[0]", equalTo("O preço deve ser positivo"));
	}
	
	@Test
	public void insertShoultReturnUnprocessableEntityWhenAdminLoggeAndPriceIsZero() {
		postProductInstance.put("price", 0);
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(422)
			.body("errors.message[0]", equalTo("O preço deve ser positivo"));
	}
	
	@Test
	public void insertShoultReturnUnprocessableEntityWhenAdminLoggeAndProductHasNoCategory() {
		postProductInstance.put("categories", null);
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + adminToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(422)
			.body("errors.message[0]", equalTo("Deve ter pelo menos uma categoria"));
	}
	
	@Test
	public void insertShoultReturnForbiddenWhenClientLogged() {
		JSONObject newProduct = new JSONObject(postProductInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(403);
	}
	
	@Test
	public void insertShoultReturnUnunthorizedWhenInvalidToken() {
		JSONObject newProduct = new JSONObject(postProductInstance);
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + invalidToken )
			.body(newProduct)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/products")
		.then()
			.statusCode(401);
	}
	
	@Test
	public void deleteShoultReturnNoContentWhenIdExistsAndAdminLogged() {
		existProductId = 25L;
		given()
			.header("Authorization", "Bearer " + adminToken)
		.when()
			.delete("/products/{id}", existProductId)
		.then()
			.statusCode(204);
	}
	
	@Test
	public void deleteShoultReturnNotFoundWhenIdIsNotExistsAndAdminLogged() {
		nonExistingProductId = 100L;
		given()
			.header("Authorization", "Bearer " + adminToken)
		.when()
			.delete("/products/{id}", nonExistingProductId)
		.then()
			.statusCode(404)
			.body("error", equalTo("Recurso não encontrado"))
			.body("status", equalTo(404));
	}
	
	@Test
	public void deleteShoultReturnBadRequestWhenDependentIdAndAdminLogged() {
		dependentProductId = 3L;
		given()
			.header("Authorization", "Bearer " + adminToken)
		.when()
			.delete("/products/{id}", dependentProductId)
		.then()
			.statusCode(400);
	}
	
	@Test
	public void deleteShoultReturnForbidenWhenClientLogged() {
		existProductId = 25L;
		given()
			.header("Authorization", "Bearer " + clientToken)
		.when()
			.delete("/products/{id}", existProductId)
		.then()
			.statusCode(403);
	}
	
	@Test
	public void deleteShoultReturnUnauthorizedWhenInvalidToken() {
		existProductId = 25L;
		given()
			.header("Authorization", "Bearer " + invalidToken)
		.when()
			.delete("/products/{id}", existProductId)
		.then()
			.statusCode(401);
	}
}
