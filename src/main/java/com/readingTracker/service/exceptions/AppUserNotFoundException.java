package com.readingTracker.service.exceptions;

public class AppUserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 202204001L;

	public AppUserNotFoundException(Long id) {
		super("Could not find user " + id);
	}
}
