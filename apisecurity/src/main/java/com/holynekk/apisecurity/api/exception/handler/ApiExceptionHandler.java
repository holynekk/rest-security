package com.holynekk.apisecurity.api.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private static final ApiErrorMessage GENERIC_ERROR_MESSAGE = new ApiErrorMessage("Sorry, some error happened!");

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ApiErrorMessage> handleSQLException(SQLException e) {
        LOG.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(GENERIC_ERROR_MESSAGE);
    }
}
