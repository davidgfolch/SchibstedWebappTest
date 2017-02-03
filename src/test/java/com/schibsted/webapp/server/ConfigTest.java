package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.schibsted.webapp.server.exception.ConfigurationException;
import com.schibsted.webapp.server.injector.ConfigInjector;

public class ConfigTest extends ConfigInjector {
	
	@Test
	public void configDefaultVal() throws Exception {
	    assertTrue("defaultRes".equals(config.get("Non existent key","defaultRes")));
	}

	@Test
	public void configError() throws Exception {
		try {
			Config.getConfig(ConfigTest.class); //non existent configTest.properites
			fail();
		} catch (ConfigurationException e) {
			assertTrue(true);
		}
	}

}

