package com.norgini.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.dtos.ClientDTO;
import com.norgini.entities.Client;
import com.norgini.services.ClientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

	private ClientService service;
	private ModelMapper mapper;

	@PostMapping
	public ResponseEntity<ClientDTO> create(@RequestBody @Valid ClientDTO obj) {
		Client client = service.create(obj);
		ClientDTO entityToDto = mapper.map(client, ClientDTO.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityToDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO obj) {
		obj.setId(id);
		Client updatedClient = service.update(obj);

		if (updatedClient != null) {
			ClientDTO updatedClientDTO = mapper.map(updatedClient, ClientDTO.class);
			return ResponseEntity.ok().body(updatedClientDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable @NonNull Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Client>> getClients() {
		List<Client> clients = service.findClients();
		return ResponseEntity.ok(clients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Client> getClient(@PathVariable @NonNull Long id) {
		Client client = service.findClient(id);
		return ResponseEntity.ok(client);
	}

}
