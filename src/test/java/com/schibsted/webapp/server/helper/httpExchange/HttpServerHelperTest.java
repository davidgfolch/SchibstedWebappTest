package com.schibsted.webapp.server.helper.httpExchange;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.IOException;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.model.Parameter;

public class HttpServerHelperTest extends ServerHttpExchangeBaseTest {

	private static final String PARAM_TO_ENCODE = "&enc";
	
	@Before
	public void before() {
		hook.setListener(this);
		try {
			serverTestHelper.getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void encode() {
		assertFalse(PARAM_TO_ENCODE.equals(parameterHelper.encode(PARAM_TO_ENCODE)));
	}

	@Test
	public void permissionDenied() {
		try {
			HttpServerHelper.permissionDenied(httpExchange);
		} catch (IOException e) {
			assertTrue(e.getMessage().equals("headers already sent"));
		}
		// assertFalse(httpExchange.getResponseCode()==HttpStatus.SC_FORBIDDEN);
	}

	@Test
	public void notRedirect() {
		assertFalse(HttpServerHelper.isRedirect(httpExchange));
	}

	@Test
	public void redirect() {
		try {
			HttpServerHelper.redirect(httpExchange, "/page1");
		} catch (IOException e) {
			assertTrue(e.getMessage().equals("headers already sent"));
		}
		// assertTrue(HttpServerHelper.isRedirect(httpExchange)); for page1 not
		// running testController hook don't work
	}

	private final Parameter param = new Parameter("param", "paramValue");
	private final Parameter param1 = new Parameter("param1", "param1Value");
	private final Parameter param2 = new Parameter("param2", "param2Value");
	private final Parameter paramNewVal = new Parameter("param", "paramNewVal");

	@Test
	public void setParameter() {
		assertTrue(parameterHelper.setUriParameter("/prueba", param).contains(param.toString()));
		assertTrue(parameterHelper.setUriParameter("/prueba", param).contains(param.toString()));
		assertTrue(parameterHelper.setUriParameter("/prueba?" + param, paramNewVal).contains(paramNewVal.toString()));
		assertTrue(parameterHelper.setUriParameter("/prueba?" + param1, paramNewVal).contains(paramNewVal.toString()));

		assertTrue(serverTestHelper.contains(
				parameterHelper.setUriParameter("/prueba?" + param1 + "&" + param, paramNewVal),
				paramNewVal.toString(), param1.toString()));

		assertEquals("/prueba?"+param1+"&"+paramNewVal+"&"+param2, 
				parameterHelper.setUriParameter("/prueba?"+param1+"&"+param+"&"+param2, paramNewVal));
	}

	@Test
	public void setParameters() {
		assertEquals("/prueba?"+param.toString()+"&"+param1.toString(),parameterHelper.setUriParameters("/prueba", param, param1));
	}

}
