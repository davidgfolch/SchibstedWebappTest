package com.schibsted.webapp.server.helper;

public class StringHelper {
	
	private StringHelper() {
	}

	public static boolean isNotEmpty(String s) {
		return s!=null && s.trim().length()>0;
	}

}
