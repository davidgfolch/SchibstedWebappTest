package com.schibsted.webapp.server.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class User {

	List<Role> roles;
	String name;
	String password; // TODO: encript passwords

	public User(String name, String password, String... roles) {
		this(Arrays.asList(roles).stream().map(Role::new).collect(Collectors.toList()), name, password);
	}

	public User(List<Role> roles, String name, String password) {
		super();
		this.roles = roles;
		this.name = name;
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRole(List<Role> role) {
		this.roles = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
