package com.norgini.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.dtos.LoginDTO;
import com.norgini.dtos.RefreshTokenDTO;
import com.norgini.dtos.TokenDTO;
import com.norgini.entities.RefreshToken;
import com.norgini.entities.User;
import com.norgini.services.RefreshTokenService;
import com.norgini.services.TokenService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LoginController {

	private AuthenticationManager manager;
	private TokenService tokenService;
	private RefreshTokenService refreshTokenService;

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dados) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getLogin(), dados.getPassword());
		var authentication = manager.authenticate(authenticationToken);
		var token = tokenService.tokenGenerator((UserDetails) authentication.getPrincipal());
		var user = (UserDetails) authentication.getPrincipal();
		var refreshToken = refreshTokenService.createRefreshToken(user, token);
		return new ResponseEntity<TokenDTO>(new TokenDTO(token, refreshToken.getRefreshToken(), user.getUsername()),
				HttpStatus.CREATED);
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenDTO> refreshtoken(@Valid @RequestBody RefreshTokenDTO request) {
		String requestRefreshToken = request.getRefreshToken();
		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> getToken(requestRefreshToken, user))
				.orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
	}

	private ResponseEntity<TokenDTO> getToken(String requestRefreshToken, User user) {
		String token = tokenService.tokenGenerator(user);
		refreshTokenService.deleteByUser(user);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user, token);
		return ResponseEntity.ok(new TokenDTO(token, refreshToken.getRefreshToken(), user.getEmail()));
	}

	@DeleteMapping("/revoke")
	public ResponseEntity<?> revokeToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) auth.getPrincipal();
		refreshTokenService.deleteByUser(user);
		return ResponseEntity.noContent().build();
	}

}
