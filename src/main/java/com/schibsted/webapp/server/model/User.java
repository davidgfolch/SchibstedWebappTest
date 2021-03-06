package com.schibsted.webapp.server.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

	List<Role> roles;
	String name;
	String password;
	
	public User() {
		super();
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
