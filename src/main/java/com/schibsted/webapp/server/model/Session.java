package com.schibsted.webapp.server.model;

import java.util.LinkedHashMap;

public class Session extends LinkedHashMap<String, Object> {

	public static final String LOGGED_USER = "loggedUser";
	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private Long lastUsed;

	public Session(String uuid, Long lastUsed) {
		super();
		this.uuid = uuid;
		this.lastUsed = lastUsed;
	}

	public Long getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Long lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	public void setLoggedUser(User user) {
		put(LOGGED_USER,user);
	}

	public Object getLoggedUser() {
		return get(LOGGED_USER);
	}

}
