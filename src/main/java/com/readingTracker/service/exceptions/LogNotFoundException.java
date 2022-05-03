package com.readingTracker.service.exceptions;

public class LogNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 202205001L;
	
	public LogNotFoundException(Long id) {
		super("Could not find log " + id);
	}
}
