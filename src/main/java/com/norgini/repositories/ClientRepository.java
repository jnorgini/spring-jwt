package com.norgini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.norgini.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
