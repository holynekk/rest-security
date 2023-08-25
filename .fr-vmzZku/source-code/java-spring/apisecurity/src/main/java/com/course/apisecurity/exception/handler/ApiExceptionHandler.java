package com.course.apisecurity.exception.handler;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

	private static final String GENERIC_ERROR_MESSAGE = "Sorry, some sql error happened";

	@ExceptionHandler({ SQLException.class })
	public ResponseEntity<ApiErrorMessage> handleSqlException(SQLException e) {
		LOG.error(e.getMessage());

		var body = new ApiErrorMessage(GENERIC_ERROR_MESSAGE);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header(HttpHeaders.CONTENT_TYPE, "application/json").body(body);
	}

}
