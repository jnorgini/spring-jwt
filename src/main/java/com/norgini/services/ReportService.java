package com.norgini.services;

import org.springframework.stereotype.Service;

import com.norgini.entities.Report;
import com.norgini.repositories.ReportRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

	private ReportRepository repository;

	public Report findById(Long id) {
		return repository.findById(id).get();
	}

	public Report create(Report obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
