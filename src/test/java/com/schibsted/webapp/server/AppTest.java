package com.schibsted.webapp.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.schibsted.webapp.controller.web.LoginController;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.model.Parameter;

public class AppTest extends ServerHttpExchangeBaseTest {
	
	private static String LOGIN_PATH=Server.getConfig().get("login.path");
	
	@Test
	public void _302() throws IOException  {
	    assertTrue(HttpStatus.SC_MOVED_TEMPORARILY==ServerTestHelper.getResponseCode("/page1"));
	}
	
	@Test
	public void _404() throws IOException  {
	    assertTrue(HttpStatus.SC_NOT_FOUND==ServerTestHelper.getResponseCode("/page4"));
	}

	@Test
	public void _200() throws IOException  {
	    assertTrue(HttpStatus.SC_OK==ServerTestHelper.getResponseCode(LOGIN_PATH));
	}

	@Test
	public void loginFail() throws IOException {
		assertFalse(doLogin("user1","XXXX","page1").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}

	@Test
	public void logout() throws IOException {
		assertTrue(doLogout());
	}

	@Test
	public void login() throws IOException {
//		assertTrue(logout());
		assertTrue(doLogin("user1","user1","").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}
	
	@Test()
	public void permissionDenied() throws IOException {
		System.out.println("################################################################");
		String urlRedirection=ServerTestHelper.SERVER_URL+"/page2";
		try {
			doLogin("user1","user1","/page2",false);
		} catch (IOException e) {
			assertTrue(e.getMessage().equals("Server returned HTTP response code: 403 for URL: "+urlRedirection));
		}
		//int code=ServerTestHelper.getResponseCode("/page2");
		//System.out.println("################"+code+"##################");
		//assertTrue(code==HttpStatus.SC_FORBIDDEN);
//		System.out.println("################################################################");
//		assertTrue(body.contains("Login page")); //todo: pseudo test for sonar testing coverage (without cookies in httpconnection dont work)
//		System.out.println("################################################################");
	}
	
	/**
	 * connection implementation don't save cookies, should not redirect to page1 cause session is lost
	 * @throws IOException 
	 */
	@Test
	public void loginRedirect() throws IOException {
		assertFalse(doLogin("user1","user1","page1").contains("<h1>Page1</h1>")); 
	}

	private boolean doLogout() throws IOException {
		return ServerTestHelper.getResponseCode("/logout")==HttpStatus.SC_MOVED_TEMPORARILY;
	}

	private String doLogin(String user, String pwd, String redirect) throws IOException {
		return doLogin(user,pwd,redirect,true);
	}
	private String doLogin(String user, String pwd, String redirect, boolean autoFollowRedirects) throws IOException {
		String postParams=loginParams(user,pwd);
		String url="/login?redirect="+redirect;
		return ServerTestHelper.getResponseBody(url,postParams,autoFollowRedirects);
	}
	
	private String loginParams(String user, String pwd) {
		String postParams=ParameterHelper.setUriParameters("/uri", new Parameter("user.name",user),new Parameter("user.password",pwd));
		return postParams.replaceAll("/uri\\?", "");
	}

//	@Test
//	public void _403()  {
//	    assertTrue(HttpStatus.SC_FORBIDDEN==getResponseCode(serverUrl+LOGIN_PATH));
//	}

}

