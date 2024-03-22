package com.norgini.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.norgini.repositories.RefreshTokenRepository;
import com.norgini.repositories.UserRepository;
import com.norgini.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

	private TokenService service;
	private UserRepository repository;
	private RefreshTokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var tokenJWT = tokenRecovery(request);

		if (tokenJWT != null) {
			try {
				var subject = service.getUsername(tokenJWT);
				tokenRepository.findByToken(tokenJWT).orElseThrow();
				var usuario = repository.findByUsername(subject);

				var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (RuntimeException e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

	private String tokenRecovery(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}

}
