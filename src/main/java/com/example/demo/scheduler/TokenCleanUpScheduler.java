package com.example.demo.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserTokenRepository;

import jakarta.transaction.Transactional;

@Component
public class TokenCleanUpScheduler {

    private final UserTokenRepository tokenRepository;

    public TokenCleanUpScheduler(UserTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    @Transactional
    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void deleteExpiredTokens() {

        tokenRepository.deleteByExpiryAtBefore(LocalDateTime.now());

        System.out.println("Expired tokens deleted at : " + LocalDateTime.now());
    }
}