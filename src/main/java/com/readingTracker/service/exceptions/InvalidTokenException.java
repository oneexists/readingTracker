package com.readingTracker.service.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 202205001L;

	public InvalidTokenException(HttpServletResponse response, Exception exception) throws StreamWriteException, DatabindException, IOException {
		response.setHeader("error", exception.getMessage());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		Map<String, String> error = new HashMap<>();
		error.put("error_message", exception.getMessage());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), error);
	}

}
