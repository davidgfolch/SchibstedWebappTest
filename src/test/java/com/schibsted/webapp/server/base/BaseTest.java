package com.schibsted.webapp.server.base;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaseTest {
	
	private static final String JUL_MANAGER = "java.util.logging.manager";
	private static final String LOG4J_JUL_MANAGER = "org.apache.logging.log4j.jul.LogManager";

	static {
		System.setProperty(JUL_MANAGER, LOG4J_JUL_MANAGER);
//		LoggingConfiguration.inicialize();
	}
	
	@Test
	public void systemPropertySet() {
		assertEquals(LOG4J_JUL_MANAGER,System.getProperty(JUL_MANAGER));
	}

}
