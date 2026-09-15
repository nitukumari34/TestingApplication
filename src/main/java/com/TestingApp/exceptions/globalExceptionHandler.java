package com.TestingApp.exceptions;

import com.TestingApp.config.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = new ApiError( exception.getMessage(),
                HttpStatus.NOT_FOUND );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
