package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.ServerTestHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.model.Parameter;

public class HttpServerHelperTest extends ServerHttpExchangeBaseTest {

	private static final String PARAM_TO_ENCODE = "&enc";

	@Before
	public void before() {
		hook.setListener(this);
		try {
			ServerTestHelper.getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void encode() {
		assertFalse(PARAM_TO_ENCODE.equals(ParameterHelper.encode(PARAM_TO_ENCODE)));
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

	Parameter param = new Parameter("param", "paramValue");
	Parameter param1 = new Parameter("param1", "param1Value");
	Parameter param2 = new Parameter("param2", "param2Value");
	Parameter paramNewVal = new Parameter("param", "paramNewVal");

	@Test
	public void setParameter() {
		assertTrue(ParameterHelper.setUriParameter("/prueba", param).contains(param.toString()));
		assertTrue(ParameterHelper.setUriParameter("/prueba", param).contains(param.toString()));
		assertTrue(ParameterHelper.setUriParameter("/prueba?" + param, paramNewVal).contains(paramNewVal.toString()));
		assertTrue(ParameterHelper.setUriParameter("/prueba?" + param1, paramNewVal).contains(paramNewVal.toString()));

		assertTrue(ServerTestHelper.contains(
				ParameterHelper.setUriParameter("/prueba?" + param1 + "&" + param, paramNewVal),
				paramNewVal.toString(), param1.toString()));

		assertEquals("/prueba?"+param1+"&"+paramNewVal+"&"+param2, 
				ParameterHelper.setUriParameter("/prueba?"+param1+"&"+param+"&"+param2, paramNewVal));
	}

	@Test
	public void setParameters() {
		assertEquals("/prueba?"+param.toString()+"&"+param1.toString(),ParameterHelper.setUriParameters("/prueba", param, param1));
	}

}
