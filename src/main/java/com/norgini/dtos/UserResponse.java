package com.norgini.dtos;

import com.norgini.entities.Role;
import com.norgini.entities.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private Long id;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private Role role;
	private UserStatus status;

}
