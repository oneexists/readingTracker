package com.readingTracker.service.exceptions;

public class AppUserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 202205002L;

	public AppUserNotFoundException(Long id) {
		super("Could not find user " + id);
	}

	public AppUserNotFoundException(String username) {
		super("Could not find user " + username);
	}
}
