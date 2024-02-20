package com.devsuperior.uri2621.dto;

import com.devsuperior.uri2621.projections.ProductNameProjetion;

public class ProductDTO {

	private String name;
	
	public ProductDTO() {
	}

	public ProductDTO(String name) {
		this.name = name;
	}
	
	public ProductDTO(ProductNameProjetion projetion) {
		name = projetion.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductDTO [name=" + name + "]";
	}
}
