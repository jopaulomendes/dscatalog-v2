package com.devsuperior.dscommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.tests.UserDetailsFactory;
import com.devsuperior.dscommerce.tests.UserFactory;
import com.devsuperior.dscommerce.util.CustomUserUtil;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
	private CustomUserUtil customUserUtil;

	private String existUserName, nonExisitUserName;
	private User user;
	private List<UserDetailsProjection> userDetails;

	@BeforeEach
	void setUp() throws Exception {
		existUserName = "maria@gmail.com";
		nonExisitUserName = "seumenino@gmail.com";

		user = UserFactory.createCustomClientUser(1L, existUserName);

		userDetails = UserDetailsFactory.createCustomAdminUser(existUserName);

		Mockito.when(repository.searchUserAndRolesByEmail(existUserName)).thenReturn(userDetails);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExisitUserName)).thenReturn(new ArrayList<>());

		Mockito.when(repository.findByEmail(existUserName)).thenReturn(Optional.of(user));
		Mockito.when(repository.findByEmail(nonExisitUserName)).thenReturn(Optional.empty());
	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(existUserName);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existUserName);
	}

	@Test
	public void loadUserByUsernameShouldThromUsernameNotFoundExceptionWhenUserDoesNotExist() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExisitUserName);
		});
	}

	@Test
	public void authenticateShoulreturnUserWhenUserExists() {
		Mockito.when(customUserUtil.getLoggedUserName()).thenReturn(existUserName);

		User result = service.authenticated();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existUserName);
	}

	@Test
	public void authenticateThromUsernameNotFoundExceptionWhenUserDoesNotExists() {
		Mockito.doThrow(ClassCastException.class).when(customUserUtil).getLoggedUserName();

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.authenticated();
		});
	}
}
