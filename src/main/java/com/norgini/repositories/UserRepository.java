package com.norgini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.norgini.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
