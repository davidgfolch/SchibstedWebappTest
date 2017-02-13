package com.schibsted.webapp.server.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.exception.DataException;
import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

@Singleton
public final class UserHelper {

	private static List<User> users = new ArrayList<>(InMemory.getUsers());

	public boolean hasUserRole(User user, String roleRequired, String roleAdmin) {
		if (roleRequired == null)
			return true;
		if (user == null || user.getRoles().isEmpty())
			return false;
		return user.getRoles().stream() //
				.map(Role::getName) //
				.filter(a -> (roleAdmin != null && roleAdmin.equals(a)) || a.equals(roleRequired)) //
				.count() > 0;
	}

	public User getUser(String userName) {
		return users.stream() //
				.filter(u -> userName!=null && userName.equals(u.getName())) //
				.findFirst().orElse(null);
	}

	public List<User> getUsers(String userName) {
		return users.stream() //
				.filter(u -> userName != null && u.getName().contains(userName)) //
				.collect(Collectors.toList());
	}

	public User checkCreadentials(String username, String password) {
		User user = getUser(username);
		if (user == null || !EncryptHelper.checkPassword(password, user.getPassword()))
			return null;
		return user;
	}

	public static List<Role> createRoles(String... roles) {
		return Arrays.stream(roles) //
				.map(Role::new) //
				.collect(Collectors.toList());
	}

	public void delete(String userName) throws DataException {
		User user=getUser(userName);
		if (user==null)
			throw new DataException("User not found or more than one result");
		users.remove(user);
	}

}
