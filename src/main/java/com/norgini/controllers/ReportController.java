package com.norgini.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.entities.Report;
import com.norgini.services.ReportService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {

	private ReportService service;

	@GetMapping("/{id}")
	public ResponseEntity<Report> getReport(@PathVariable Long id) {
		Report find = service.findById(id);
		return ResponseEntity.ok(find);
	}

	@PostMapping
	public ResponseEntity<Report> create(@RequestBody Report report) {
		Report obj = service.create(report);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
