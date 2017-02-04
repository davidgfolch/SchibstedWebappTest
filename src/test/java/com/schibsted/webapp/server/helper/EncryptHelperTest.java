package com.schibsted.webapp.server.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class EncryptHelperTest {
	
	@Test
	public void test() {
		String pass="passwdTry1";
		String encrypted=EncryptHelper.encript(pass);
		assertTrue("Encription don't work",EncryptHelper.checkPassword(pass,encrypted));
		encrypted=EncryptHelper.encript(pass+"ERROR");
		assertFalse("Encription don't work",EncryptHelper.checkPassword(pass,encrypted));
	}

}
