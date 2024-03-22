package com.norgini.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norgini.dtos.ClientDTO;
import com.norgini.entities.Client;
import com.norgini.repositories.ClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {
	
	private ClientRepository repository;
	private ModelMapper mapper;
	
	@Transactional
	public Client create(ClientDTO obj) {
		return repository.save(mapper.map(obj, Client.class));
	}
	
	@Transactional
	public Client update(ClientDTO obj) {
		Long id = obj.getId();
		Client product = repository.findById(id).get();
		product = mapper.map(obj, Client.class);
		return repository.save(product);
	}
	
	@Transactional
	public void delete(@NonNull Long id) {
		repository.deleteById(id);
	}
	
	public List<Client> findClients() {
		return repository.findAll();
	}
	
	public Client findClient(@NonNull Long id) {
		return repository.findById(id).get();
	}

}
