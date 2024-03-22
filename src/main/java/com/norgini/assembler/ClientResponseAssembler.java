package com.norgini.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.norgini.dtos.ClientResponse;
import com.norgini.entities.Client;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientResponseAssembler {

	private final ModelMapper mapper;

	public ClientResponse toModel(Client client) {
		return mapper.map(client, ClientResponse.class);
	}
	
	public List<ClientResponse> toCollectModel(List<Client> clients) {
		return clients.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
