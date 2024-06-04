package com.mariamkatamashvili.trainerwork.security;

import com.mariamkatamashvili.trainerwork.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthValidator {
    private final JwtTokenProvider jwtTokenProvider;

    public void validate(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (!jwtTokenProvider.isTokenValid(token)) {
                throw new AuthenticationException("Invalid JWT token", "401");
            }
        } else {
            throw new AuthenticationException("JWT token missing or invalid", "401");
        }
    }
}