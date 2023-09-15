package com.jopaulo.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jopaulo.dscatalog.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
public class UserDTO implements Serializable{

	private Long id;
	@NotBlank(message = "Nome obrigatório")
	@Size(min = 3, message = "O nome do usuário deve ter no mínimo 3 caracteres")
	@Size(max = 50, message = "O nome do produto deve ter no máximno 50 caracteres")
	private String firstName;
	@Size(min = 3, message = "O sobrenome do usuário deve ter no mínimo 3 caracteres")
	@Size(max = 50, message = "O nome do produto deve ter no máximno 50 caracteres")
	@NotBlank(message = "Sobrenome obrigatório")
	private String lastName;
	@NotBlank(message = "e-mail")
	@Email(message = "E-mail inválido")
	private String email;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}

	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<RoleDTO> getRoles() {
		return roles;
	}
}
