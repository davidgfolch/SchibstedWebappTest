package webapp.helper.notImplemented;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.schibsted.webapp.server.helper.HttpServerHelper;

public class HttpServerHelperTest {
	
	private static final String PARAM_TO_ENCODE = "&enc";
//	private HttpExchangeMock ex=new HttpExchangeMock();
	
	@Test
	public void encode() {
		assertFalse("Encoder don't work",PARAM_TO_ENCODE.equals(HttpServerHelper.encode(PARAM_TO_ENCODE)));
	}
//	@Test
//	public void permissionDenied() {
//		try {
//			HttpServerHelper.permissionDenied(ex);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		assertFalse("permissionDenied don't work",ex.getResponseCode()==HttpStatus.SC_FORBIDDEN);
//	}
//	@Test
//	public void redirect() {
//		assertFalse("isRedirect and/or redirect don't work",HttpServerHelper.isRedirect(ex));
//		try {
//			HttpServerHelper.redirect(ex,"/login");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		assertTrue("isRedirect and/or redirect don't work",HttpServerHelper.isRedirect(ex));
//	}

}
