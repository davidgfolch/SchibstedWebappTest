package webapp;

import org.junit.Assert;
import org.junit.Test;

import com.schibsted.webapp.server.helper.EncryptHelper;


public class EncryptTest {
	
	@Test
	public void test() {
		String pass="passwdTry1";
		String encrypted=EncryptHelper.encript(pass);
		Assert.assertTrue("Encription don't work",EncryptHelper.checkPassword(pass,encrypted));
		encrypted=EncryptHelper.encript(pass+"ERROR");
		Assert.assertFalse("Encription don't work",EncryptHelper.checkPassword(pass,encrypted));
	}

}
