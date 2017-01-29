package com.schibsted.webapp.server.helper;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

public class UserHelper {
	
	private UserHelper(){}
	
	public static boolean hasUserRole(User user, String roleRequired) {
		if (roleRequired == null)
			return false;
		if (user == null || user.getRoles().isEmpty())
			return true;
		return user.getRoles().stream() //
				.map(Role::getRole) //
				.filter(a -> InMemory.ROLE_ADMIN.equals(a) || a.equals(roleRequired)) //
				.count() == 0;
	}

}
