package com.schibsted.webapp.server.helper;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Encryption utility See http://jasypt.org/
 * 
 * @author slks
 */
public final class EncryptHelper {

	private static final StrongPasswordEncryptor PWD_ENCRIPTOR = new StrongPasswordEncryptor();

	private EncryptHelper() {
	}

	public static boolean checkPassword(String inputPassword, String encryptedPassword) {
		return PWD_ENCRIPTOR.checkPassword(inputPassword, encryptedPassword);
	}

	public static String encript(String pwd) {
		return PWD_ENCRIPTOR.encryptPassword(pwd);
	}

}
