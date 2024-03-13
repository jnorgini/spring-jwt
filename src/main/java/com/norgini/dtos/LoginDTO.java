package com.norgini.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

	@NotBlank
	private String login;

	@NotBlank
	private String password;

}
