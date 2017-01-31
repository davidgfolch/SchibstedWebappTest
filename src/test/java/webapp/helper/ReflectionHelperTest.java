package webapp.helper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.controller.LoginController;
import com.schibsted.webapp.server.helper.ReflectionHelper;

public class ReflectionHelperTest {


//	@Test
//	public void getAuthenticationRoles() {
//		ReflectionHelper.getAuthenticationRoles(ex);
//		assertTrue(ReflectionHelper.hasDefaultConstructor(LoginController.class));
//	}

	@Test
	public void hasDefaultConstructor() {
		assertTrue(ReflectionHelper.hasDefaultConstructor(LoginController.class));
	}

	@Test
	public void isControllerCandidate() {
		assertTrue(ReflectionHelper.isControllerCandidate(LoginController.class));
	}

}
