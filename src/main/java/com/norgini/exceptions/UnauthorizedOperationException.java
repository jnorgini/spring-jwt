package com.norgini.exceptions;

public class UnauthorizedOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedOperationException(String message) {
		super(message);
	}

}
