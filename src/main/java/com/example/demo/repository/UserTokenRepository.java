package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

}
