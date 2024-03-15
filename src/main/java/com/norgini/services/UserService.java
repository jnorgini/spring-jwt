package com.norgini.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norgini.dtos.RegisterDTO;
import com.norgini.entities.User;
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
	public User create(RegisterDTO registerDTO) {
		User user = mapper.map(registerDTO, User.class);
		String password = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(password);
		return repository.save(user);
	}

	@Transactional
	public User update(Long id, RegisterDTO registerDTO) {
		User existingUser = this.find(id);
		mapper.map(registerDTO, existingUser);
		String password = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
		existingUser.setPassword(password);
		return repository.save(existingUser);
	}

	@Transactional
	public void delete(Long id) {
	    refreshTokenRepository.deleteById(id); 
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
			return org.springframework.security.core.userdetails.User.builder().password(user.getPassword())
					.username(user.getUsername()).build();
		} else {
			throw new UsernameNotFoundException("");
		}
	}

}
