package com.norgini.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norgini.assembler.UserRequestDisassembler;
import com.norgini.assembler.UserResponseAssembler;
import com.norgini.dtos.UserRequest;
import com.norgini.dtos.UserResponse;
import com.norgini.entities.User;
import com.norgini.exceptions.UnauthorizedOperationException;
import com.norgini.exceptions.UserNotFoundException;
import com.norgini.repositories.RefreshTokenRepository;
import com.norgini.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository repository;
	private RefreshTokenRepository refreshTokenRepository;
	private final UserResponseAssembler userResponseAssembler;
	private final UserRequestDisassembler userRequestDisassembler;

	@Transactional
	public UserResponse create(UserRequest userRequest) {
		User user = userRequestDisassembler.toDomainObject(userRequest);
		String password = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(password);
		User savedUser = repository.save(user);
		return userResponseAssembler.toModel(savedUser);
	}

	@Transactional
	public UserResponse update(Long id, UserRequest userRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var currentUser = (User) auth.getPrincipal();
		if (!currentUser.getId().equals(id)) {
			throw new UnauthorizedOperationException("You do not have permission to update this user.");
		}
		User existingUser = repository.findById(id).get();
		userRequestDisassembler.copyToDomainObject(userRequest, existingUser);
		String password = new BCryptPasswordEncoder().encode(userRequest.getPassword());
		existingUser.setPassword(password);
		User updatedUser = repository.save(existingUser);
		return userResponseAssembler.toModel(updatedUser);
	}

	@Transactional
	public void delete(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var currentUser = (User) auth.getPrincipal();
		if (!currentUser.getId().equals(id)) {
			throw new UnauthorizedOperationException("You do not have permission to delete this user.");
		}
		User user = repository.findById(id).get();
		refreshTokenRepository.deleteByUser(user);
		repository.deleteById(id);
	}

	public UserResponse find(Long id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
		return userResponseAssembler.toModel(user);
	}

	public List<UserResponse> findAll() {
		List<User> users = repository.findAll();
		return userResponseAssembler.toCollectModel(users);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user != null) {
			return org.springframework.security.core.userdetails
					.User.builder()
					.password(user.getPassword())
					.username(user.getUsername()).build();
		} else {
			throw new UsernameNotFoundException("");
		}
	}

}
