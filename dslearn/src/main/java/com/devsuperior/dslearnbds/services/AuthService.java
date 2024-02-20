package com.devsuperior.dslearnbds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.UserRepository;
import com.devsuperior.dslearnbds.services.exceptions.ForbiddenException;
import com.devsuperior.dslearnbds.services.exceptions.UnauthorizeException;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true) //somente leitura
	public User authenticated() {
		try {
			// pega o usuário logado (nome usuário)
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return repository.findByEmail(username);
		} catch (Exception e) {
			throw new UnauthorizeException("Usuário inválido");
		}
	}
	
	public void validateSelfOrAdmin(Long userId) {
		User user = authenticated();
		if (!user.getId().equals(userId) && !user.hasHole("ROLE_ADMIN")) {
			throw new ForbiddenException("Usuário não autorizado");
		}
	}
}
