package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;

import java.io.OutputStream;
import java.net.URI;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.schibsted.webapp.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class WebContextHandler implements HttpHandler {

	private static final String TWIG_HTML = ".twig.html";
	private static final String TEMPLATES = "templates";
//	public static final Logger LOG = Logger.getLogger(WebContextHandler.class);
	private static final Logger LOG = LogManager.getLogger(WebContextHandler.class);
	
	@Override
	public void handle(HttpExchange ex) throws IOException {
		
		//todo: sun.net.httpserver.AuthFilter
		LOG.debug(ex.getRequestMethod()+" "+ex.getProtocol()+" "+ex.getLocalAddress()+ex.getRequestURI());
		URI uri= ex.getRequestURI();
		final OutputStream os = ex.getResponseBody();
		//ex.getRequestHeaders().set("Content-Type", "text/html");
		
		try {
			//TODO: REMOVE TEMPLATE DEPENDENCY WITH A FACADE
	        JtwigTemplate template = JtwigTemplate.classpathTemplate(TEMPLATES+uri.getPath()+TWIG_HTML);
	        JtwigModel model = JtwigModel.newModel().with("user", new User("prueba","prueba"));
	        String res=template.render(model);
	        ex.sendResponseHeaders(HttpStatus.SC_OK, res.length());
	        os.write(res.getBytes());
	        //new PrintWriter(os).write(res);
		} catch (Exception e) {
			LOG.error("",e);
			ErrorHandler.handle(ex,e, os);
		} finally {
			os.flush();
			ex.close();
		}
	}

}
