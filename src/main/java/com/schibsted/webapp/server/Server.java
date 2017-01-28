package com.schibsted.webapp.server;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.contextHandler.ContextHandlerFactory;
import com.schibsted.webapp.server.filter.AuthFilter;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Filter;

@SuppressWarnings("restriction")
public class Server {

	public static final Logger LOG = LogManager.getLogger(Server.class);
	public static final String PORT = "port";
	
	public void startServer() {

		HttpServer server;
		Config config;
		try {
			config=new Config(Server.class);
			LOG.info("Starting server on port " + config.get(PORT));
			server = HttpServer.create(new InetSocketAddress(config.getInt(PORT)), 0);
		} catch (IOException e) {
			LOG.error("Cannot start server...", e);
			return;
		}
		
		//Authenticator.setDefault(new BasicAuthenticator(""));
		//server.setExecutor(executor);
		for (String contextPath: config.getList("contexts")) {
			HttpContext ctx = server.createContext(contextPath);
			String cxtHandlers=config.get("contextHandler."+contextPath);
			ctx.setHandler(ContextHandlerFactory.get(cxtHandlers));
			List<Filter> filters=ctx.getFilters();
			filters.add(new AuthFilter(config.get("login.path")));
			filters.add(new ParamsFilter());
			//ctx.setAuthenticator(new BasicAuthenticator(contextPath));
		}
		server.start();
	}

}
