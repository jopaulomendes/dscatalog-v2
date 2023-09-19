package com.jopaulo.dscatalog.dto;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

	@NotBlank(message = "Camppo obrigatório")
	private String token;
	@NotBlank(message = "Camppo obrigatório")
	@Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
	private String password;
	
	public NewPasswordDTO() {
	}
	
	public NewPasswordDTO(String token, String password) {
		super();
		this.token = token;
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, token);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewPasswordDTO other = (NewPasswordDTO) obj;
		return Objects.equals(password, other.password) && Objects.equals(token, other.token);
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
