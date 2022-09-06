package com.devcollege.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.NonUniqueResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import com.devcollege.exceptions.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<String,String>> handleEmptyInput(NotFoundException ex) {
		Map<String,String> errorMessage = new HashMap<>();
		errorMessage.put("message",ex.getPassedValue()+" doesn't exist " );
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Map<String,String>> handleNoDataFoundException(NoDataFoundException ex) {
		Map<String,String> errorMessage = new HashMap<>();
		errorMessage.put("error","No data Found");
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String,String> errorMessage = new HashMap<>();
		errorMessage.put("detailedMessage", ex.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Map<String,String>> handleConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
		Map<String,String> errorMessage = new HashMap<>();
		errorMessage.put("detailedMessage", ex.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<Map<String,String>> handleNonUniqueResultException(NonUniqueResultException ex) {
		Map<String,String> errorMessage = new HashMap<>();
		errorMessage.put("error","It is allocated to someone, not able to delete the course details");
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex) {
//		Map<String,String> errorMessage = new HashMap<>();
//		errorMessage.put("detailedMessage", ex.getMessage());
//		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<Map<String,String>> handleMissingPathVariableException(MissingPathVariableException ex) {
		Map<String,String> errorMessage = new HashMap<>();
		errorMessage.put("detailedMessage","Id is missing..!!");
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ApiResponseException.class)
	public ResponseEntity<ApiResponseException> handleApiResponseException (ApiResponseException ex) {
		String message = ex.getMessage();
		ApiResponseException apiResponseException = new ApiResponseException(message, false);
		return new ResponseEntity<ApiResponseException>(apiResponseException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String,String>> handleException(HttpMessageNotReadableException exception) {
		Throwable cause = exception.getCause();
		Map<String,String> errorMap = new HashMap<>();
		if (cause instanceof MismatchedInputException){
			MismatchedInputException mismatchedInputException = (MismatchedInputException) cause;
			errorMap.put( "Invalid Input. " , mismatchedInputException.getPath().get(0).getFieldName());
		}
		return new ResponseEntity<Map<String,String>>(errorMap,null,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errorMap.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
	}
}
