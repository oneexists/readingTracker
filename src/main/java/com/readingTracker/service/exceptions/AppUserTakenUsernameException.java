package com.readingTracker.service.exceptions;

public class AppUserTakenUsernameException extends RuntimeException {
	private static final long serialVersionUID = 202205001L;

	public AppUserTakenUsernameException(String username) {
		super("Username is taken (" + username + ")");
	}
}
