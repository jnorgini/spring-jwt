package com.norgini.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

	private Long id;

	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String cpf;
	
	@NotBlank
	private String phone;


}
