package com.schibsted.webapp.server.injector;

import static org.junit.Assert.fail;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.exception.ConfigurationException;

public class ConfigInjector {

	protected Config config;

	public ConfigInjector() {
		try {
			config = Config.getConfig(Server.class);
		} catch (ConfigurationException e) {
			fail(e.getMessage());
		}
	}
}
