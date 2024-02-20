package com.jopaulo.dscatalog.dto;

import java.io.Serializable;

import com.jopaulo.dscatalog.entities.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank(message = "Nome obrigatório")
	@Size(min = 3, message = "O nome da categoria deve ter no mínimo 3 caracteres")
	@Size(max = 50, message = "O nome da categoria deve ter no máximno 50 caracteres")
	private String name;
	
	public CategoryDTO() {
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
