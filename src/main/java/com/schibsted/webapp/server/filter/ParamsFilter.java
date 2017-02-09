package com.schibsted.webapp.server.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.model.Parameters;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@Singleton
@SuppressWarnings("restriction")
public class ParamsFilter extends Filter implements ILogger {

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

	private void parseGetParams(HttpExchange ex) {
		Optional.ofNullable(ex.getRequestURI().getRawQuery()) //
				.ifPresent(queryStr -> parse(queryStr).forEach(x -> getParams(ex).put(x.getKey(), x.getValue())));
	}

	private void parsePostParams(HttpExchange ex) throws IOException {
		if (POST.equals(ex.getRequestMethod())) {
			InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String query = br.lines().collect(Collectors.joining("\n"));
			parse(query).forEach(x -> getParams(ex).put(x.getKey(), x.getValue()));
		}
	}

	private List<SimpleEntry<String, String>> parse(String queryString) {
		return Arrays.asList(queryString.split("&")).stream().map(s -> Arrays.copyOf(s.split("="), 2))
				.map(o -> new SimpleEntry<String, String>(o[0], decode(o[1]))).collect(Collectors.toList());
	}

	private String decode(String string) {
		if (string == null)
			return "";
		try {
			return URLDecoder.decode(string, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			logger().fatal(e);
		}
		return null;
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