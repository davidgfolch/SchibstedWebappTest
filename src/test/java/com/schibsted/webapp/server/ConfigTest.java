package com.schibsted.webapp.server;

import static com.schibsted.webapp.di.DIFactory.inject;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.schibsted.webapp.server.base.BaseTest;
import com.schibsted.webapp.server.exception.ConfigurationException;

public class ConfigTest extends BaseTest {
	
	private final Config config=inject(Config.class);

	@Test
	public void configDefaultVal() {
		assertTrue("defaultRes".equals(config.get("Non existent key", "defaultRes")));
	}

	@Test
	public void configError() {
		try {
			//non existent configTest.properites
			Config.getConfig(ConfigTest.class);
			fail();
		} catch (ConfigurationException e) {
			assertTrue(true);
		}
	}

}
