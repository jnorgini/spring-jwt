package com.norgini.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.norgini.dtos.ErrorResponse;
import com.norgini.exceptions.UnauthorizedOperationException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandlerAdvice {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> notFound() {
		return new ResponseEntity<>("Object not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> badRequest(MethodArgumentNotValidException exception) {
		List<FieldError> errors = exception.getFieldErrors();
		var dto = errors.stream().map(e -> convertEntityToDTO(e)).toList();
		return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedOperationException.class)
	public ResponseEntity<?> unauthorized(UnauthorizedOperationException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
		ErrorResponse errorDTO = new ErrorResponse("Entity not found", ex.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> repeatEmail(SQLIntegrityConstraintViolationException ex) {
		ErrorResponse errorDTO = new ErrorResponse("Users and clients must have a unique email address.", ex.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
	}

	private ErrorResponse convertEntityToDTO(FieldError error) {
		return new ErrorResponse(error.getField(), error.getDefaultMessage());
	}

}
