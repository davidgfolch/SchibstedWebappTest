package com.schibsted.webapp.server.helper;

public final class StringHelper {

	private StringHelper() {
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() > 0;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length()==0;
	}

}
