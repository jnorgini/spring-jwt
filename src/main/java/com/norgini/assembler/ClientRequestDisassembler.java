package com.norgini.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.norgini.dtos.ClientRequest;
import com.norgini.entities.Client;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientRequestDisassembler {

	private ModelMapper mapper;

	public Client toDomainObject(ClientRequest clientRequest) {
		return mapper.map(clientRequest, Client.class);
	}

	public void copyToDomainObject(ClientRequest clientRequest, Client client) {
		mapper.map(clientRequest, client);
	}

}
