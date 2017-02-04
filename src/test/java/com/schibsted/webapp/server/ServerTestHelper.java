package com.schibsted.webapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class ServerTestHelper {

	private final String serverUrl;

	public ServerTestHelper(Config config) {
		serverUrl="http://localhost:" + config.get("port");
	}

	public URLConnection connect(String url) throws IOException {
		boolean followRedirects = false;
		return connect(url, null, followRedirects);
	}

	public URLConnection connect(String url, String data, boolean followRedirects) throws IOException {
		URL u = new URL(serverUrl + url);
		URLConnection con = u.openConnection();
		post(con, data);
		HttpURLConnection httpCon = (HttpURLConnection) con;
		HttpURLConnection.setFollowRedirects(followRedirects);
		httpCon.setInstanceFollowRedirects(followRedirects);
		con.connect();
		return httpCon;
	}

	private boolean isRedirect(HttpURLConnection con) throws IOException {
		int status = con.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
				return true;
		}
		return false;
	}

	private void post(URLConnection con, String data) throws IOException {
		if (data == null)
			return;
		con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Accept", "*/*");
		((HttpURLConnection) con).setRequestMethod("POST");
		((HttpURLConnection) con).setDoOutput(true);
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(data);
			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
		}

	}

	public int getResponseCode(String url) throws IOException {
		return ((HttpURLConnection) connect(url)).getResponseCode();
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(con.getInputStream()));
	}

	public String getResponseBody(String url) throws Exception {
		boolean followRedirects = true;
		return getResponseBody(url, null, followRedirects);
	}

	public String getResponseBody(String url, String data, boolean followRedirects) throws IOException {
		URLConnection con = connect(url, data, followRedirects);
		con=redirect(con);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return in.lines().collect(Collectors.joining("\n"));
	}

	private URLConnection redirect(URLConnection con) throws IOException {
		URLConnection conRedirect=con; 
		if (isRedirect((HttpURLConnection) con)) {
			String newUrl = serverUrl+con.getHeaderField("Location");
			conRedirect = (HttpURLConnection) new URL(newUrl).openConnection();
			conRedirect.setRequestProperty("Cookie", con.getHeaderField("Set-Cookie"));
			conRedirect=redirect(conRedirect);
		}
		return conRedirect;
	}

	public boolean contains(String container, String... content) {
		for (String str : content) {
			if (!container.contains(str))
				return false;
		}
		return true;
	}

	public String getServerURL() {
		return serverUrl;
	}

}
