package com.schibsted.webapp.server.helper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.schibsted.webapp.server.helper.httpExchange.HttpServerHelperTest;
import com.schibsted.webapp.server.helper.httpExchange.ReflectionHelperTest;
import com.schibsted.webapp.server.helper.httpExchange.SessionHelperTest;

@RunWith(Suite.class)
@SuiteClasses({ HttpServerHelperTest.class, SessionHelperTest.class, ReflectionHelperTest.class })
public class HttpExchangeTests {

}
