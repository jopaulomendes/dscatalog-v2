package com.devsuperior.dscommerce.tests;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.dscommerce.projections.UserDetailsProjection;

public class UserDetailsFactory {

	public static List<UserDetailsProjection> createCustomClientUser(String username){
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", "ROLE_CLIENT", 1L));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminUser(String username){
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", "ROLE_ADMIN	", 2L));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminClientUser(String username){
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", "ROLE_CLIENT	", 1L));
		list.add(new UserDetailsImpl(username, "123", "ROLE_ADMIN	", 2L));
		return list;
	}
}

class UserDetailsImpl implements UserDetailsProjection{
	private String username, password, authority;
	private Long roleId;
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(String username, String password, String authority, Long roleId) {
		this.username = username;
		this.password = password;
		this.authority = authority;
		this.roleId = roleId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Long getRoleId() {
		return roleId;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
