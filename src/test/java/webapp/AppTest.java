package webapp;

import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schibsted.webapp.server.Server;

public class AppTest {
	
	private static String serverUrl;
	
	@BeforeClass
	public static void beforeClass() {
		new Server().startServer();
		serverUrl="http://localhost:"+Server.getConfig().get("port");
	}
	
	@Test
	public void _302() throws Exception {
	    assertTrue(HttpStatus.SC_MOVED_TEMPORARILY==getResponseCode(serverUrl+"/page1"));
	}
	
	@Test
	public void _404() throws Exception {
	    assertTrue(HttpStatus.SC_NOT_FOUND==getResponseCode(serverUrl+"/page4"));
	}

	@Test
	public void _200() throws Exception {
	    assertTrue(HttpStatus.SC_OK==getResponseCode(serverUrl+Server.getConfig().get("login.path")));
	}

//	@Test
//	public void _403() throws Exception {
//	    assertTrue(HttpStatus.SC_FORBIDDEN==getResponseCode(serverUrl+Server.getConfig().get("login.path")));
//	}

	private int getResponseCode(String url) throws Exception {
	    URL u = new URL(url);
	    URLConnection con = u.openConnection();
	    HttpURLConnection.setFollowRedirects(false);
	    HttpURLConnection httpCon = (HttpURLConnection) con;
	    httpCon.setInstanceFollowRedirects(false);
	    con.connect();
	    return httpCon.getResponseCode();
	    //BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	}

}

