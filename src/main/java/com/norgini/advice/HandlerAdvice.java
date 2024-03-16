package com.norgini.advice;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.norgini.dtos.ErrorDTO;
import com.norgini.exceptions.UnauthorizedOperationException;

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

	private ErrorDTO convertEntityToDTO(FieldError error) {
		return new ErrorDTO(error.getField(), error.getDefaultMessage());
	}

}
