package com.schibsted.webapp.server.model;

public class Role {

	String name;

	public Role(String role) {
		super();
		this.name = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String role) {
		this.name = role;
	}

}
