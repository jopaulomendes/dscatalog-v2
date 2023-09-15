package com.jopaulo.dscatalog.dto;

import com.jopaulo.dscatalog.services.validation.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@UserInsertValid
public class UserInsertDTO extends UserDTO {

	@NotBlank(message = "Senha obrigatória")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
	private String password;
	
	public UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
