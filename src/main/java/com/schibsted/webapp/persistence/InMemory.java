package com.schibsted.webapp.persistence;

import java.util.Arrays;
import java.util.List;

import com.schibsted.webapp.server.helper.EncryptHelper;
import com.schibsted.webapp.server.model.User;

public class InMemory {
	
	public static final String ROLE_ADMIN = "ADMIN";

	private static List<User> users = Arrays.asList(new User[] {
			new User("admin", EncryptHelper.encript("admin"), ROLE_ADMIN),
			new User("user1", EncryptHelper.encript("user1"), "PAGE_1"),
			new User("user2", EncryptHelper.encript("user2"), "PAGE_3"),
			new User("user3", EncryptHelper.encript("user3"), "PAGE_4")
	});

	private InMemory() {}
	
	public static synchronized List<User> getUsers() {
		return users;
	}

}
