package com.norgini.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.norgini.services.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

	private SecurityFilter securityFilter;
	private UserService userService;
	private LoginEntryPoint loginEntryPoint;

	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userService).passwordEncoder(encoder());

		var authenticationManager = builder.build();

		return http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/users").permitAll()
						.requestMatchers(HttpMethod.POST, "/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/refresh").permitAll()
						.requestMatchers(HttpMethod.POST, "/clients").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/clients/{id}").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/clients/{id}").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.GET, "/users/me").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.GET, "/clients", "/clients/{id}").hasAnyRole("ADMIN", "USER")
						.anyRequest().permitAll())
				.authenticationManager(authenticationManager)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(handling -> handling.authenticationEntryPoint(loginEntryPoint))
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
}