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

import com.norgini.dtos.ClientRequest;
import com.norgini.dtos.ClientResponse;
import com.norgini.services.ClientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

	private ClientService service;

	@GetMapping
	public ResponseEntity<List<ClientResponse>> getClients() {
		List<ClientResponse> clients = service.findClients();
		return ResponseEntity.ok(clients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {
		ClientResponse client = service.find(id);
		return ResponseEntity.ok(client);
	}

	@PostMapping
	public ResponseEntity<ClientResponse> post(@Valid @RequestBody ClientRequest clientRequest) {
		ClientResponse newClient = service.create(clientRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientResponse> put(@PathVariable Long id, @Valid @RequestBody ClientRequest clientRequest) {
		ClientResponse updatedClient = service.update(id, clientRequest);
		return ResponseEntity.ok(updatedClient);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
