package com.schibsted.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	List<Role> role;
	String name;
	String password; //TODO: encript passwords

	public User(String name, String password) {
		this(new ArrayList<Role>(),name,password);
	}

	public User(List<Role> role, String name, String password) {
		super();
		this.role = role;
		this.name = name;
		this.password = password;
	}
	
	public List<Role> getRole() {
		return role;
	}
	public void setRole(List<Role> role) {
		this.role = role;
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
