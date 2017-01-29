package com.schibsted.webapp.persistence;

import java.util.Arrays;
import java.util.List;

import com.schibsted.webapp.server.model.User;

//TODO: INTERFACE, FACADE, FACTORY FOR PERSISTENCE IMPLEMENTATIONS
//TODO: ENCRIPT PASSWORDS
public class InMemory {
	
	public static final String ROLE_ADMIN = "ADMIN";

	private InMemory() {}
	
	private static List<User> users = Arrays.asList(new User[] {
			new User("admin", "admin",ROLE_ADMIN),
			new User("user1", "user1","PAGE_1"),
			new User("user2", "user2","PAGE_3"),
			new User("user3", "user3","PAGE_4")
	});

	public static synchronized List<User> getUsers() {
		return users;
	}

}
