package com.schibsted.webapp.persistence;

import java.util.Arrays;
import java.util.List;

import com.schibsted.webapp.server.helper.EncryptHelper;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

public final class InMemory {
	
	public static final String ROLE_ADMIN = "ADMIN";

	private static final List<User> USERS = Arrays
			.asList(new User[] { new User(roles(ROLE_ADMIN), "admin", EncryptHelper.encript("admin")),
					new User(roles("PAGE_1"), "user1", EncryptHelper.encript("user1")),
					new User(roles("PAGE_2"), "user2", EncryptHelper.encript("user2")),
					new User(roles("PAGE_3"), "user3", EncryptHelper.encript("user3")) });

	private InMemory() {
	}

	public static List<User> getUsers() {
		return USERS;
	}
	
	public static List<Role> roles(String... roles) {
		return UserHelper.createRoles(ROLE_ADMIN); 
	}

}
