package com.schibsted.webapp.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.schibsted.webapp.controller.web.LoginController;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.model.Parameter;

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
		assertFalse(doLogin("user1","XXXX","page1").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}

	@Test
	public void logout() {
		assertTrue(doLogout());
	}

	@Test
	public void login() {
//		assertTrue(logout());
		assertTrue(doLogin("user1","user1","").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}
	/**
	 * connection implementation don't save cookies, should not redirect to page1 cause session is lost
	 */
	@Test
	public void loginRedirect() {
		assertFalse(doLogin("user1","user1","page1").contains("<h1>Page1</h1>")); 
	}

	private boolean doLogout() {
		try {
			return ServerTestHelper.getResponseCode("/logout")==HttpStatus.SC_MOVED_TEMPORARILY;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private String doLogin(String user, String pwd, String redirect) {
		String postParams=ParameterHelper.setUriParameters("/uri", new Parameter("user.name",user),new Parameter("user.password",pwd),new Parameter("redirect",redirect));
		postParams=postParams.replaceAll("/uri\\?", "");
		try {
			boolean followRedirects=true;
			return ServerTestHelper.getResponseBody("/login",postParams,followRedirects);
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

