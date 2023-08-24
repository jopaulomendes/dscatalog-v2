package com.jopaulo.dscatalog.tests;

import java.time.Instant;

import com.jopaulo.dscatalog.dto.ProductDTO;
import com.jopaulo.dscatalog.entities.Category;
import com.jopaulo.dscatalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "Smartphone", "Xioame Poco 5s", 1200.00, "imagem", Instant.parse("2020-07-13T20:50:07.123450Z"));
		product.getCategories().add(new Category(2L, "Eletrônicos"));
		return product;
	}
	
	public static ProductDTO createDto() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
}
