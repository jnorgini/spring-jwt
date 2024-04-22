package com.norgini.controllers;

import java.util.List;

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
import com.norgini.dtos.UserResponse;
import com.norgini.entities.User;
import com.norgini.entities.UserStatus;
import com.norgini.exceptions.UnauthorizedOperationException;
import com.norgini.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private UserService service;

	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
		UserResponse createdUser = service.create(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
		UserResponse updatedUser = service.update(id, userRequest);
		return ResponseEntity.ok(updatedUser);
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
	public ResponseEntity<List<UserResponse>> getUsers() {
		List<UserResponse> users = service.findAll();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
			User loggedInUser = (User) auth.getPrincipal();
			for (UserResponse user : users) {
				if (user.getId().equals(loggedInUser.getId())) {
					user.setStatus(UserStatus.ONLINE);
				}
			}
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
		UserResponse user = service.find(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
			User loggedInUser = (User) auth.getPrincipal();
			if (loggedInUser.getId().equals(id)) {
				user.setStatus(UserStatus.ONLINE);
			}
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping("/me")
	public ResponseEntity<User> getMe() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) auth.getPrincipal();
		user.setStatus(UserStatus.ONLINE);
		return ResponseEntity.ok(user);
	}

}
