package com.schibsted.webapp.server.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpServerHelper {
	
	private static final Logger LOG = LogManager.getLogger(HttpServerHelper.class);

	private HttpServerHelper() {}

	public static void redirect(HttpExchange ex, String path) throws IOException {
		ex.getResponseHeaders().add(HttpHeaders.LOCATION, path);
		ex.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, 0);
		ex.close();
	}

	public static void permissionDenied(HttpExchange ex) throws IOException {
		ex.sendResponseHeaders(HttpStatus.SC_FORBIDDEN, 0);
		ex.close();
	}

	public static boolean isRedirect(HttpExchange ex) {
		return ex.getResponseCode()==HttpStatus.SC_MOVED_TEMPORARILY;
	}

	public static String setUriParameter(String uriPath, String parameter, String paramValue) {
		String encodedValue=encode(paramValue);
		String parameterEq=parameter+"=";
		try {
			String q=new URI(uriPath).getRawQuery();
			if (q==null || q.length()==0) {
				return uriPath+"?"+parameterEq+encodedValue;
			} else if (q.contains(parameterEq)){
				return uriPath+"?"+q.replaceAll(parameterEq+"^(&)+",parameterEq+encodedValue); //todo: check this works
			}
			return uriPath+"?"+q+"&"+parameterEq+encodedValue;
		} catch (URISyntaxException e) {
			LOG.error("",e);
		}
		//URI.create(uriPath);
		return null;
	}
	
	public static String encode(String paramValue) {
		try {
			return URLEncoder.encode(paramValue,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("",e);
			return paramValue;
		}
	}

}
