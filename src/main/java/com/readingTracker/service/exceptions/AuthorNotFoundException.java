package com.readingTracker.service.exceptions;

public class AuthorNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 202205001L;

	public AuthorNotFoundException(Long id) {
		super("Could not find author " + id);
	}

}
