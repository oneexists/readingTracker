package com.readingTracker.web.dto;

public class AppUserLoginDto {
	private final String username;
	private final String password;

	public AppUserLoginDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
