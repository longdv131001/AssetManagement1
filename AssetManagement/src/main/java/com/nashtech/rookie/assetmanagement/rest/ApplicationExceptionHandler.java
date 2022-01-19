package com.nashtech.rookie.assetmanagement.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nashtech.rookie.assetmanagement.dto.AppException;
import com.nashtech.rookie.assetmanagement.dto.ErrorResponse;


@ControllerAdvice
public class ApplicationExceptionHandler {
    private ResponseEntity<ErrorResponse> buildErrorResponse(String errorMessage, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(status.value(), errorMessage);
        return new ResponseEntity<>(error, status);
    }
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(IllegalArgumentException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(AppException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(HttpMessageNotReadableException e) {
		//logger e.getMessage
		//return buildErrorResponse("Json", HttpStatus.BAD_REQUEST);
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BindException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
