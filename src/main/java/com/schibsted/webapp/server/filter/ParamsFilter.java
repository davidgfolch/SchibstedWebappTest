package com.schibsted.webapp.server.filter;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class ParamsFilter extends Filter {

	private static final String PARAMETERS = "parameters";
	private static final String UTF_8 = "utf-8";
	private static final String POST = "POST";

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		parseGetParams(ex);
		parsePostParams(ex);
		chain.doFilter(ex);
	}

	private void parseGetParams(HttpExchange ex) throws UnsupportedEncodingException {
		String query = ex.getRequestURI().getRawQuery();
		if (query == null)
			return;
		getParams(ex).addAll(URLEncodedUtils.parse(query, Charset.defaultCharset()));
	}

	private void parsePostParams(HttpExchange ex) throws IOException {
		if (POST.equals(ex.getRequestMethod())) {
			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine(); //TODO: CHECK IF WITH ONLY 1 READLINE IS OK
			getParams(ex).addAll(URLEncodedUtils.parse(query, Charset.defaultCharset()));
		}
	}

	@SuppressWarnings("unchecked")
	private List<NameValuePair> getParams(HttpExchange ex) {
		if (ex.getAttribute(PARAMETERS) == null)
			ex.setAttribute(PARAMETERS, new ArrayList<NameValuePair>());
		Object params = ex.getAttribute(PARAMETERS);
		return (List<NameValuePair>) params;
	}

	@Override
	public String description() {
		return "Obtains request parameters";
	}

}