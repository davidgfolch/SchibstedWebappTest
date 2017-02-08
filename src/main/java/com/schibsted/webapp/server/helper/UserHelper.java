package com.schibsted.webapp.server.helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

public final class UserHelper {

	private UserHelper() {
	}

	public static boolean hasUserRole(User user, String roleRequired, String roleAdmin) {
		if (roleRequired == null)
			return true;
		if (user == null || user.getRoles().isEmpty())
			return false;
		return user.getRoles().stream() //
				.map(Role::getName) //
				.filter(a -> (roleAdmin != null && roleAdmin.equals(a)) || a.equals(roleRequired)) //
				.count() > 0;
	}

	public static User getUser(List<User> users, String userName) {
		if (userName == null)
			return null;
		return users.stream() //
				.filter(u -> userName.equals(u.getName())) //
				.findFirst().orElse(null);
	}

	public static User checkCreadentials(List<User> users, String username, String password) {
		User user = getUser(users, username);
		if (user == null || !EncryptHelper.checkPassword(password, user.getPassword()))
			return null;
		return user;
	}

	public static List<Role> createRoles(String[] roles) {
		return Arrays.stream(roles) //
				.map(Role::new) //
				.collect(Collectors.toList());
	}

}
