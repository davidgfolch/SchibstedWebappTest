package com.schibsted.webapp.server.helper;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.inject.Singleton;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.model.Parameter;

@Singleton
public class ParameterHelper implements ILogger {

	public String setUriParameter(String uriPath, Parameter param) {
		return setUriParameter(uriPath, param.getName(), param.getValue());
	}

	public String encode(String paramValue) {
		try {
			return URLEncoder.encode(paramValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger().error("", e);
			return paramValue;
		}
	}

	public String setUriParameters(String uri, Parameter... parameters) {
		String res = uri;
		for (Parameter param : parameters) {
			res = setUriParameter(res, param);
		}
		return res;
	}

	public String setUriParameter(String uriPath, String paramName, String paramValue) {
		String parameterEq = paramName + "=";
		String encodedValue = encode(paramValue);
		String uriNoParams = uriPath.replaceAll("\\?.*", "");
		try {
			String q = new URI(uriPath).getRawQuery();
			if (q == null || q.length() == 0) {
				return uriNoParams + "?" + parameterEq + encodedValue;
			} else if (q.contains(parameterEq)) {
				return uriNoParams + "?" + q.replaceAll(parameterEq + "([^&]*)", parameterEq + encodedValue);
			}
			return uriNoParams + "?" + q + "&" + parameterEq + encodedValue;
		} catch (URISyntaxException e) {
			logger().error("", e);
		}
		return null;
	}

}
