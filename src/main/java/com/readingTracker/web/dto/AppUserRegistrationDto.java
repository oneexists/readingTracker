package com.readingTracker.web.dto;

public class AppUserRegistrationDto {
	private String name;
	private String username;
	private String password;
	private String dateOfBirth;

	public AppUserRegistrationDto() {
	}

	public AppUserRegistrationDto(String name, String username, String password, String dateOfBirth) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
