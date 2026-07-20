package com.example.demo.service;

import java.time.LocalDateTime;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.UserToken;
import com.example.demo.exceptions.InvalidCredentialsException;
import com.example.demo.exceptions.InvalidTokenException;
import com.example.demo.exceptions.TokenNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTokenRepository;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserTokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
//	private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
            UserTokenRepository tokenRepository,
            JwtUtil jwtUtil) {

    		this.userRepository = userRepository;
    		this.tokenRepository = tokenRepository;
    		this.jwtUtil = jwtUtil;
//    		this.passwordEncoder = passwordEncoder;
    }


    public LoginResponse login(LoginRequest request) {

    	User user = userRepository.findByUsername(request.getUsername())
    	        .orElseThrow(() ->
    	            new InvalidCredentialsException("Invalid Credentials"));

//    	if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new InvalidCredentialsException("Invalid Credentials");
//        }
    	
    	if (!user.getPassword().equals(request.getPassword())) {
    	    throw new InvalidCredentialsException("Invalid Credentials");
    	}
    	
        String token = jwtUtil.generateToken(user);

        UserToken userToken = new UserToken();
        userToken.setUsername(user.getUsername());
        userToken.setToken(token);
        userToken.setIssuedAt(LocalDateTime.now());
        userToken.setExpiryAt(LocalDateTime.now().plusMinutes(15));
        userToken.setActive(true);

        tokenRepository.save(userToken);

        return new LoginResponse(token);
    }

    public void logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Authorization Header Missing");
        }

        String token = authHeader.substring(7);

        UserToken userToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token Not Found"));

        userToken.setActive(false);

        tokenRepository.save(userToken);
    }

}