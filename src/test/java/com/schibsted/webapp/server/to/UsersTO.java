package com.schibsted.webapp.server.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schibsted.webapp.server.model.User;

public class UsersTO {

	@JsonProperty("users")
	List<User> users;

	public final List<User> getUsers() {
		return users;
	}

	public final void setUsers(List<User> users) {
		this.users = users;
	}

}
