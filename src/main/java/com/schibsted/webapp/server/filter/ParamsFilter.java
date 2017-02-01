package com.schibsted.webapp.server.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.apache.http.client.utils.URLEncodedUtils;

import com.schibsted.webapp.server.model.Parameters;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class ParamsFilter extends Filter {

	public static final String PARAMETERS = "parameters";
	private static final String UTF_8 = "utf-8";
	private static final String POST = "POST";

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		getParams(ex); // initialize params (empty list, avoid
						// nullPointerExceptions)
		parseGetParams(ex);
		parsePostParams(ex);
		chain.doFilter(ex);
	}

	private void parseGetParams(HttpExchange ex) throws UnsupportedEncodingException {
		String query = ex.getRequestURI().getRawQuery();
		if (query == null)
			return;
		URLEncodedUtils.parse(query, Charset.defaultCharset())
				.forEach(x -> getParams(ex).put(x.getName(), x.getValue()));
	}

	private void parsePostParams(HttpExchange ex) throws IOException {
		if (POST.equals(ex.getRequestMethod())) {
			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String query = br.lines().collect(Collectors.joining("\n"));
			URLEncodedUtils.parse(query, Charset.defaultCharset())
					.forEach(x -> getParams(ex).put(x.getName(), x.getValue()));
		}
	}

	private Parameters getParams(HttpExchange ex) {
		if (ex.getAttribute(PARAMETERS) == null)
			ex.setAttribute(PARAMETERS, new Parameters());
		Object params = ex.getAttribute(PARAMETERS);
		return (Parameters) params;
	}

	@Override
	public String description() {
		return "Obtains request parameters";
	}

}