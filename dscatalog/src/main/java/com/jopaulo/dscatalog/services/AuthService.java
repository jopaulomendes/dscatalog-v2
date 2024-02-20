package com.jopaulo.dscatalog.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jopaulo.dscatalog.dto.EmailDTO;
import com.jopaulo.dscatalog.dto.NewPasswordDTO;
import com.jopaulo.dscatalog.entities.PasswordRecover;
import com.jopaulo.dscatalog.entities.User;
import com.jopaulo.dscatalog.repositories.PasswordRecoverRepository;
import com.jopaulo.dscatalog.repositories.UserRepository;
import com.jopaulo.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {
	
	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;
	
	@Value("${email.password-recover.uri}")
	private String recoverUri;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;
	
	@Autowired
	private EmailService emailService;

	@Transactional
	public void createRecoverToken(EmailDTO body) {
		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("E-mail não encontrado");
		}
		
		String token= UUID.randomUUID().toString();
		
		PasswordRecover entity = new PasswordRecover();
		entity.setEmail(body.getEmail());
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
		entity = passwordRecoverRepository.save(entity);
		
		String text = "Acesse o link para definir uma nova senha\n\n"
				+ recoverUri + token + ". Validade de " + tokenMinutes + " minutos.";
		
		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);
	}

	@Transactional
	public void searchValidTokens(NewPasswordDTO body) {
		List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
		if (result.size() == 0) {
			throw new ResourceNotFoundException("Token inválido");
		}
		
		User user = userRepository.findByEmail(result.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user = userRepository.save(user);
	}
	
	protected User authentication() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaimAsString("username");
			return userRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuário inválido");
		}
	}

}
