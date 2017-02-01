package com.schibsted.webapp.server;

import static org.junit.Assert.*;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.schibsted.webapp.controller.LoginController;

public class AppTest extends ServerHttpExchangeBaseTest {
	
	@Test
	public void _302() throws Exception {
	    assertTrue(HttpStatus.SC_MOVED_TEMPORARILY==ServerTestHelper.getResponseCode("/page1"));
	}
	
	@Test
	public void _404() throws Exception {
	    assertTrue(HttpStatus.SC_NOT_FOUND==ServerTestHelper.getResponseCode("/page4"));
	}

	@Test
	public void _200() throws Exception {
	    assertTrue(HttpStatus.SC_OK==ServerTestHelper.getResponseCode(Server.getConfig().get("login.path")));
	}

	@Test
	public void loginFail() {
		assertFalse(login("user1","XXXX").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}

	@Test
	public void login() {
		assertTrue(login("user1","user1").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}
	
	private String login(String user, String pwd) {
		String data="user.name="+user+"&user.password="+pwd;
		try {
			return ServerTestHelper.getResponseBody("/login",data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

//	@Test
//	public void _403() throws Exception {
//	    assertTrue(HttpStatus.SC_FORBIDDEN==getResponseCode(serverUrl+Server.getConfig().get("login.path")));
//	}

}

