package com.norgini.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norgini.entities.Client;
import com.norgini.exceptions.ClientNotFoundException;
import com.norgini.repositories.ClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {

	private ClientRepository repository;

	@Transactional
	public Client create(Client client) {
		return repository.save(client);
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Client find(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
	}

	public List<Client> findClients() {
		return repository.findAll();
	}

}
