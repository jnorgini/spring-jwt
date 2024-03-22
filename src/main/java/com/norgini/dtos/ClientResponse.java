package com.norgini.dtos;

import com.norgini.entities.ClientStatus;

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
	private ClientStatus status;

}
