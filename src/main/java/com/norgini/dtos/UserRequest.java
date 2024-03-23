package com.norgini.dtos;

import com.norgini.entities.Role;
import com.norgini.entities.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	
	private Long id;

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String password;
	
	@NotNull
	private Role role;
	
	@NotNull
	private UserStatus status;

}
