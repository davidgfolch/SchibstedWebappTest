package com.schibsted.webapp.server.filter;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class AuthFilter extends Filter {

	private static final String SCHIBSTED_SESSION = "ss";
	
	private String loginPath="/login";

	public AuthFilter(String loginPath) {
		this.loginPath=loginPath;
	}

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		String s=(String)ex.getAttribute(SCHIBSTED_SESSION);
		boolean isLoginPage=ex.getRequestURI()!=null && loginPath.equals(ex.getRequestURI().toString());
		if (s==null && !isLoginPage) {
		    ex.getResponseHeaders().add(HttpHeaders.LOCATION, loginPath);
		    ex.getResponseHeaders().add("Set-Cookie", "redirect="+loginPath);
		    ex.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, 0);
		    ex.close();
		} else chain.doFilter(ex);
	}

	@Override
	public String description() {
		return "Ensures all defined paths are authenticated";
	}

}
