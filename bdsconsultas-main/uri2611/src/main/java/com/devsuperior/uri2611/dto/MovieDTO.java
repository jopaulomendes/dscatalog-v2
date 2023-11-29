package com.devsuperior.uri2611.dto;

import com.devsuperior.uri2611.projections.MovieNameProjection;

public class MovieDTO {

	private Long id;
	private String name;
	
	public MovieDTO() {
	}

	public MovieDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public MovieDTO(MovieNameProjection projection) {
		id = projection.getId();
		name = projection.getName();
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

	@Override
	public String toString() {
		return "MovieDTO [id=" + id + ", name=" + name + "]";
	}
}
