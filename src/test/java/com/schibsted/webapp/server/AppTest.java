package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.schibsted.webapp.server.Server;

public class AppTest extends BaseServerHttpExchangeTest {
	
	@Test
	public void _302() throws Exception {
	    assertTrue(HttpStatus.SC_MOVED_TEMPORARILY==getResponseCode("/page1"));
	}
	
	@Test
	public void _404() throws Exception {
	    assertTrue(HttpStatus.SC_NOT_FOUND==getResponseCode("/page4"));
	}

	@Test
	public void _200() throws Exception {
	    assertTrue(HttpStatus.SC_OK==getResponseCode(Server.getConfig().get("login.path")));
	}

//	@Test
//	public void _403() throws Exception {
//	    assertTrue(HttpStatus.SC_FORBIDDEN==getResponseCode(serverUrl+Server.getConfig().get("login.path")));
//	}

}

