package com.readingTracker.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LogNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(LogNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String logNotFoundHandler(LogNotFoundException ex) {
		return ex.getMessage();
	}
}
