package com.norgini.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

	private Long id;
	private String name;
	private String email;
	private Long cpf;
	private Long phone;

}
