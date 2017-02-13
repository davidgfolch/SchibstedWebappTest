package com.schibsted.webapp.server.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schibsted.webapp.server.exception.DataException;
import com.schibsted.webapp.server.model.User;

public class UsersTO {

	@JsonProperty("users")
	List<User> users;
	@JsonProperty("user")
	User user;
	@JsonProperty("exception")
	DataException exception;

	
	public final List<User> getUsers() {
		return users;
	}

	public final void setUsers(List<User> users) {
		this.users = users;
	}

	public final User getUser() {
		return user;
	}

	public final void setUser(User user) {
		this.user = user;
	}

	public final DataException getException() {
		return exception;
	}

	public final void setException(DataException exception) {
		this.exception = exception;
	}

}
