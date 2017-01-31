package com.schibsted.webapp.server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ServerTestHelper {

	private static final String SERVER_URL="http://localhost:"+Server.getConfig().get("port");
	
	public static URLConnection connect(String url) throws Exception {
	    URL u = new URL(SERVER_URL+url);
	    URLConnection con = u.openConnection();
	    HttpURLConnection.setFollowRedirects(false);
	    HttpURLConnection httpCon = (HttpURLConnection) con;
	    httpCon.setInstanceFollowRedirects(false);
	    con.connect();
	    return httpCon;
	}
	
	public static int getResponseCode(String url) throws Exception {
	    return ((HttpURLConnection)connect(url)).getResponseCode();
	    //BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	}

//	public int getResponseBody(String url) throws Exception {
//	URLConnection con=connect(url);
//    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//}

}
