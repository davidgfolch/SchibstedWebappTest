package com.schibsted.webapp.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class AuthFilter extends Filter {

	private static final Logger LOG = LogManager.getLogger(AuthFilter.class);

	private String loginPath = "/login";

	public AuthFilter(String loginPath) {
		this.loginPath = loginPath;
	}

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		boolean isLoginPage = ex.getRequestURI() != null && loginPath.equals(ex.getRequestURI().toString());
		if (!SessionHelper.isAuthenticated(ex) && !isLoginPage) {
			LOG.debug("Redirecting to login: {}", loginPath);
			HttpServerHelper.redirect(ex,loginPath);
		} else
			chain.doFilter(ex);
	}

	@Override
	public String description() {
		return "Ensures all defined paths are authenticated";
	}

}
