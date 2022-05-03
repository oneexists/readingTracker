package com.readingTracker.service.exceptions;

public class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 202205001L;
	
	public BookNotFoundException(Long id) {
		super("Could not find book " + id);
	}

}
