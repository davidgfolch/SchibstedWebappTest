package com.schibsted.webapp.server.filter;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class AuthFilter extends Filter {

	private static final String SCHIBSTED_SESSION = "ss";

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		String s=(String)ex.getAttribute(SCHIBSTED_SESSION);
		boolean isLoginPage=ex.getRequestURI()!=null && "/login".equals(ex.getRequestURI().toString());
		if (s==null && !isLoginPage) {
		    ex.getResponseHeaders().add("Location", "/login");
		    ex.getResponseHeaders().add("Set-Cookie", "redirect=/login");
		    ex.sendResponseHeaders(302, 0);
		    ex.close();
		} else chain.doFilter(ex);
	}

	@Override
	public String description() {
		return "Ensures all defined paths are authenticated";
	}

}
