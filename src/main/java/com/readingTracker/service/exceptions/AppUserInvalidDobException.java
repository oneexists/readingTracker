package com.readingTracker.service.exceptions;

public class AppUserInvalidDobException extends RuntimeException {
	private static final long serialVersionUID = 202205001L;

	public AppUserInvalidDobException(String dateOfBirth) {
		super("Invalid date of birth (" + dateOfBirth + ")");
	}
}
