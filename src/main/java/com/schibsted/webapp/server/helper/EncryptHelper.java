package com.schibsted.webapp.server.helper;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Encryption utility 
 * @author slks
 * @see http://jasypt.org/
 */
public class EncryptHelper {
	
	private static final StrongPasswordEncryptor pwdEncryptor = new StrongPasswordEncryptor();
	
	private EncryptHelper() {}

	public static boolean checkPassword(String inputPassword, String encryptedPassword) {
		return pwdEncryptor.checkPassword(inputPassword, encryptedPassword);
	}
	
	public static String encript(String pwd) {
		return pwdEncryptor.encryptPassword(pwd);
	}
	
}
