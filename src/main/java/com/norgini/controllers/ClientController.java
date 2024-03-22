package com.norgini.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.assembler.ClientRequestDisassembler;
import com.norgini.assembler.ClientResponseAssembler;
import com.norgini.dtos.ClientRequest;
import com.norgini.dtos.ClientResponse;
import com.norgini.entities.Client;
import com.norgini.services.ClientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

	private ClientService service;
	private ClientResponseAssembler clientResponseAssembler;
	private ClientRequestDisassembler clientRequestDisassembler;

	@GetMapping
	public ResponseEntity<List<ClientResponse>> getClients() {
		List<Client> clients = service.findClients();
		return ResponseEntity.ok(clientResponseAssembler.toCollectModel(clients));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {
		Client currentClient = service.find(id);
		return ResponseEntity.ok(clientResponseAssembler.toModel(currentClient));
	}

	@PostMapping
	public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest clientRequest) {
		Client newClient = service.create(clientRequestDisassembler.toDomainObject(clientRequest));
		return ResponseEntity.status(HttpStatus.CREATED).body(clientResponseAssembler.toModel(newClient));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientResponse> update(@PathVariable Long id, @Valid @RequestBody ClientRequest clientRequest) {
		Client currentClient = service.find(id);
		clientRequestDisassembler.copyToDomainObject(clientRequest, currentClient);
		Client updatedClient = service.create(currentClient);
		return ResponseEntity.ok(clientResponseAssembler.toModel(updatedClient));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
