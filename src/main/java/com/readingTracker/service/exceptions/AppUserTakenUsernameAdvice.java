package com.readingTracker.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppUserTakenUsernameAdvice {

	@ResponseBody
	@ExceptionHandler(AppUserTakenUsernameException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String appUserTakenUsernameHandler(AppUserTakenUsernameException ex) {
		return ex.getMessage();
	}
}
