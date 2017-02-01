package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.schibsted.webapp.server.exception.ConfigurationException;

public class ConfigTest {
	
	@Test
	public void configDefaultVal() throws Exception {
	    assertTrue("defaultRes".equals(Server.getConfig().get("Non existent key","defaultRes")));
	}

	@Test
	public void configError() throws Exception {
		try {
			new Config(ConfigTest.class); //non existent configTest.properites
			fail();
		} catch (ConfigurationException e) {
			assertTrue(true);
		}
	}

}

