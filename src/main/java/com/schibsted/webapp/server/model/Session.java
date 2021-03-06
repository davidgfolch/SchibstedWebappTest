package com.schibsted.webapp.server.model;

import java.util.concurrent.ConcurrentHashMap;

public class Session extends ConcurrentHashMap<String, Object> {

	public static final String LOGGED_USER = "loggedUser";
	private static final long serialVersionUID = 1L;

	private String uuid;
	private Long lastUsed;

	private Session() {
		super();
	}

	public Session(String uuid, Long lastUsed) {
		this();
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
		put(LOGGED_USER, user);
	}

	public User getLoggedUser() {
		return (User) get(LOGGED_USER);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Session))
			return false;
		return this.uuid.equals(((Session) o).getUuid());
	}

	@Override
	public int hashCode() {
		return uuid.hashCode() ^ super.hashCode();
	}

}
