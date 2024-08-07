package com.norgini.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.norgini.entities.RefreshToken;
import com.norgini.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);
	Optional<RefreshToken> findByRefreshToken(String refreshToken);

	@Modifying
	int deleteByUser(User user);

}
