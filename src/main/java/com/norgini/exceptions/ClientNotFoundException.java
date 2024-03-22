package com.norgini.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ClientNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;

	public ClientNotFoundException(String message) {
		super(message);
	}

	public ClientNotFoundException(Long id) {
		this(String.format("There is no client with the id %d", id));
	}

}
