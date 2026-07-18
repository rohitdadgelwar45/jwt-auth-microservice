package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.UserToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTokenRepository;
import com.example.demo.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository tokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid Username"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(user);

        UserToken userToken = new UserToken();

        userToken.setUsername(user.getUsername());
        userToken.setToken(token);
        userToken.setIssuedAt(LocalDateTime.now());
        userToken.setExpiryAt(LocalDateTime.now().plusHours(1));
        userToken.setActive(true);

        tokenRepository.save(userToken);

        return new LoginResponse(token);
    }
}