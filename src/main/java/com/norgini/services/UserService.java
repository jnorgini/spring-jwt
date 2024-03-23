package com.norgini.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norgini.dtos.UserRequest;
import com.norgini.entities.User;
import com.norgini.exceptions.UnauthorizedOperationException;
import com.norgini.repositories.RefreshTokenRepository;
import com.norgini.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository repository;
	private RefreshTokenRepository refreshTokenRepository;
	private ModelMapper mapper;

	@Transactional
	public User create(UserRequest userRequest) {
		User user = mapper.map(userRequest, User.class);
		String password = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(password);
		return repository.save(user);
	}

	@Transactional
	public User update(Long id, UserRequest userRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var currentUser = (User) auth.getPrincipal();
		if (!currentUser.getId().equals(id)) {
			throw new UnauthorizedOperationException("You do not have permission to update this user.");
		}
		User existingUser = this.find(id);
		mapper.map(userRequest, existingUser);
		String password = new BCryptPasswordEncoder().encode(userRequest.getPassword());
		existingUser.setPassword(password);
		return repository.save(existingUser);
	}

	@Transactional
	public void delete(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var currentUser = (User) auth.getPrincipal();
		if (!currentUser.getId().equals(id)) {
			throw new UnauthorizedOperationException("You do not have permission to delete this user.");
		}
		User user = this.find(id);
		refreshTokenRepository.deleteByUser(user);
		repository.deleteById(id);
	}

	public User find(Long id) {
		return repository.findById(id).get();
	}

	public List<User> findAll() {
		return repository.findAll();
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
