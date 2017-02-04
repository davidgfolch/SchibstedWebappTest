package com.schibsted.webapp.server;

import com.schibsted.webapp.server.exception.ConfigurationException;
import com.schibsted.webapp.server.injector.ConfigInjector;
import org.junit.*;
import static org.junit.Assert.*;

public class ConfigTest extends ConfigInjector {
	
	@Test
	public void configDefaultVal() {
	    assertTrue("defaultRes".equals(config.get("Non existent key","defaultRes")));
	}

	@Test
	public void configError() {
		try {
			Config.getConfig(ConfigTest.class); //non existent configTest.properites
			fail();
		} catch (ConfigurationException e) {
			assertTrue(true);
		}
	}

}

