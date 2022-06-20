package com.readingTracker.web.dto;

public class JwtResponse {
	private final String token;
	private final String type;
	private final String id;
	private final String username;
	private final String roles;

	public JwtResponse(String token, String type, String id, String username, String roles) {
		super();
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getRoles() {
		return roles;
	}

}
