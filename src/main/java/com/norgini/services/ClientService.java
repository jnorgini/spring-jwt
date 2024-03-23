package com.norgini.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norgini.assembler.ClientRequestDisassembler;
import com.norgini.assembler.ClientResponseAssembler;
import com.norgini.dtos.ClientRequest;
import com.norgini.dtos.ClientResponse;
import com.norgini.entities.Client;
import com.norgini.exceptions.ClientNotFoundException;
import com.norgini.repositories.ClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {

	private ClientRepository repository;
	private final ClientResponseAssembler clientResponseAssembler;
	private final ClientRequestDisassembler clientRequestDisassembler;

	public List<ClientResponse> findClients() {
		List<Client> clients = repository.findAll();
		return clientResponseAssembler.toCollectModel(clients);
	}

	public ClientResponse find(Long id) {
		Client client = repository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
		return clientResponseAssembler.toModel(client);
	}	

	@Transactional
	public ClientResponse create(ClientRequest clientRequest) {
		Client client = clientRequestDisassembler.toDomainObject(clientRequest);
		Client savedClient = repository.save(client);
		return clientResponseAssembler.toModel(savedClient);
	}

	@Transactional
	public ClientResponse update(Long id, ClientRequest clientRequest) {
		Client existingClient = repository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
		clientRequestDisassembler.copyToDomainObject(clientRequest, existingClient);
		Client updatedClient = repository.save(existingClient);
		return clientResponseAssembler.toModel(updatedClient);
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
