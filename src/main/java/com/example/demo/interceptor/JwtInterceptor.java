package com.example.demo.interceptor;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.UserToken;
import com.example.demo.repository.UserTokenRepository;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.web.method.HandlerMethod;

import com.example.demo.annotation.RequireScope;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final UserTokenRepository tokenRepository;

    public JwtInterceptor(JwtUtil jwtUtil,
                          UserTokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        // Read Authorization Header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or Invalid Authorization Header");
            return false;
        }

        // Extract JWT Token
        String token = authHeader.substring(7);

        // Validate JWT Signature & Expiry
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or Expired Token");
            return false;
        }

        // Check Token in Database
        Optional<UserToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token not found");
            return false;
        }

        UserToken userToken = optionalToken.get();

        // Check Active Status
        if (!Boolean.TRUE.equals(userToken.getActive())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is inactive");
            return false;
        }
        
     // Check if handler is a controller method
        if (handler instanceof HandlerMethod handlerMethod) {

            // Read @RequireScope annotation
            RequireScope requireScope =
                    handlerMethod.getMethodAnnotation(RequireScope.class);

            // If no annotation, allow request
            if (requireScope == null) {
                return true;
            }

            // Extract scopes from JWT
            List<String> userScopes = jwtUtil.extractScopes(token);

            String requiredScope = requireScope.value();

            // Check permission
            if (!userScopes.contains(requiredScope)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Insufficient Permissions");
                return false;
            }
        }

        // Allow Request
        return true;
    }
}