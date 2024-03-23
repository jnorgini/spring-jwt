package com.norgini.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.dtos.UserRequest;
import com.norgini.entities.User;
import com.norgini.exceptions.UnauthorizedOperationException;
import com.norgini.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	private UserService service;
	private ModelMapper mapper;

	@PostMapping
	public ResponseEntity<UserRequest> create(@RequestBody @Valid UserRequest userRequest) {
		User createdUser = service.create(userRequest);
		UserRequest createdUserDTO = mapper.map(createdUser, UserRequest.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserRequest> update(@PathVariable Long id,
			@RequestBody @Valid UserRequest userRequest) {
		User updatedUser = service.update(id, userRequest);
		UserRequest updatedUserDTO = mapper.map(updatedUser, UserRequest.class);
		return ResponseEntity.ok(updatedUserDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} catch (UnauthorizedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = service.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User user = service.find(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/me")
	public ResponseEntity<User> getMe() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) auth.getPrincipal();
		return ResponseEntity.ok(user);
	}

}
