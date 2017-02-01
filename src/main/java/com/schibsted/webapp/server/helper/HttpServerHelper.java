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

import com.schibsted.webapp.server.model.Parameter;
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

	public static String setUriParameter(String uriPath, Parameter param) {
		return setUriParameter(uriPath, param.getName(), param.getValue());
	}
	
	public static String encode(String paramValue) {
		try {
			return URLEncoder.encode(paramValue,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("",e);
			return paramValue;
		}
	}

	public static String setUriParameters(String uri, Parameter... parameters) {
		String res=uri;
		for (Parameter param: parameters) {
			res=setUriParameter(res,param);
		}
		return res;
	}

	public static String setUriParameter(String uriPath, String paramName, String paramValue) {
		String parameterEq=paramName+"=";
		String encodedValue=encode(paramValue);
		String uriNoParams=uriPath.replaceAll("\\?.*", "");
		try {
			String q=new URI(uriPath).getRawQuery();
			if (q==null || q.length()==0) {
				return uriNoParams+"?"+parameterEq+encodedValue;
			} else if (q.contains(parameterEq)){
				return uriNoParams+"?"+q.replaceAll(parameterEq+"([^&]*)",parameterEq+encodedValue);
			}
			return uriNoParams+"?"+q+"&"+parameterEq+encodedValue;
		} catch (URISyntaxException e) {
			LOG.error("",e);
		}
		return null;
	}

}
