package com.schibsted.webapp.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.Test;

import com.schibsted.webapp.controller.web.LoginController;
import com.schibsted.webapp.server.base.ServerBaseTest;
import com.schibsted.webapp.server.model.Parameter;

public class WebappTest extends ServerBaseTest {

	@Test
	public void statusCode302() throws IOException {
		assertTrue(HttpURLConnection.HTTP_MOVED_TEMP == serverTestHelper.getResponseCode("/page1"));
	}

	@Test
	public void statusCode404() throws IOException {
		assertTrue(HttpURLConnection.HTTP_NOT_FOUND == serverTestHelper.getResponseCode("/page4"));
	}

	@Test
	public void statusCode200() throws IOException {
		assertTrue(HttpURLConnection.HTTP_OK == serverTestHelper.getResponseCode(config.get("login.path")));
	}

	@Test
	public void loginFail() throws IOException {
		assertFalse(doLogin("user1", "XXXX", "page1").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}

	@Test
	public void logout() throws IOException {
		assertTrue(doLogout());
	}

	@Test
	public void login() throws IOException {
		// assertTrue(logout());
		assertTrue(doLogin("user1", "user1", "").contains(LoginController.MSG_LOGGED_IN_SUCCESSFULY));
	}

	@Test()
	public void permissionDenied() throws IOException {
		String urlRedirection = serverTestHelper.getServerURL() + "/page2";
		try {
			doLogin("user1", "user1", "/page2", false);
		} catch (IOException e) {
			assertTrue(e.getMessage().equals("Server returned HTTP response code: 403 for URL: " + urlRedirection));
		}
	}

	/**
	 * connection implementation don't save cookies, should not redirect to
	 * page1 cause session is lost
	 * 
	 * @throws IOException
	 */
	@Test
	public void loginRedirect() throws IOException {
		assertFalse(doLogin("user1", "user1", "page1").contains("<h1>Page1</h1>"));
	}

	private boolean doLogout() throws IOException {
		return serverTestHelper.getResponseCode("/logout") == HttpURLConnection.HTTP_MOVED_TEMP;
	}

	private String doLogin(String user, String pwd, String redirect) throws IOException {
		return doLogin(user, pwd, redirect, true);
	}

	private String doLogin(String user, String pwd, String redirect, boolean autoFollowRedirects) throws IOException {
		String postParams = loginParams(user, pwd);
		String url = "/login?redirect=" + redirect;
		return serverTestHelper.getResponseBody(url, postParams, autoFollowRedirects);
	}

	private String loginParams(String user, String pwd) {
		String postParams = parameterHelper.setUriParameters("/uri", new Parameter("user.name", user),
				new Parameter("user.password", pwd));
		return postParams.replaceAll("/uri\\?", "");
	}

}
