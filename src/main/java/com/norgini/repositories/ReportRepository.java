package com.norgini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.norgini.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
