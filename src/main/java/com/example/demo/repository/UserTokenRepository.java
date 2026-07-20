package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	
	Optional<UserToken> findByToken(String token);
	
	void deleteByExpiryAtBefore(LocalDateTime time);

}
