package com.readingTracker.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppUserInvalidDobAdvice {

	@ResponseBody
	@ExceptionHandler(AppUserInvalidDobException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String appUserInvalidDobHandler(AppUserInvalidDobException ex) {
		return ex.getMessage();
	}
}
