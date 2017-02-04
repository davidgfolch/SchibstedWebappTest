package com.schibsted.webapp.server.helper;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

public class UserHelperTest {

	private final List<User> users=InMemory.getUsers();
	private final String testUserName="user1";
	private final String testUserPwd="user1";
	
	private final String[] roles= new String[]{InMemory.ROLE_ADMIN,"PAGE_1"};

	@Test
	public void checkCreadentials() {
		assertNull(UserHelper.checkCreadentials(InMemory.getUsers(), null, testUserPwd));
		assertNotNull(UserHelper.checkCreadentials(InMemory.getUsers(), testUserName, testUserPwd));
	}

	@Test
	public void createRoles() {
		assertTrue(
				UserHelper.createRoles(roles) //
				.stream() //
				.map(Role::getName)
				.filter(a->roles[0].equals(a) || roles[1].equals(a))
				.toArray()
				.length==roles.length);
	}
	
	@Test
	public void hasUserRole() {
		assertFalse(UserHelper.hasUserRole(null, "PAGE_1", InMemory.ROLE_ADMIN));
		assertTrue(UserHelper.hasUserRole(users.get(0), null, InMemory.ROLE_ADMIN));
		assertTrue(UserHelper.hasUserRole(users.get(0), "PAGE_1", InMemory.ROLE_ADMIN));
	}

	@Test
	public void getUser() {
		assertNull(UserHelper.getUser(users, null));
		assertNotNull(UserHelper.getUser(users, testUserName));
	}

}
