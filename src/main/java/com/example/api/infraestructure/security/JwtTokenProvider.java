package com.example.api.infraestructure.security;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.example.api.domain.auth.model.AuthenticatedUser;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final JwtDecoder jwtDecoder;

    public JwtTokenProvider(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public boolean validateToken(String token) {
        if (token == null || token.isBlank()) {
            log.warn("Token nulo ou vazio");
            return false;
        }
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException ex) {
            log.warn("Token invalido", ex);
            return false;
        }
    }

    public long getExpirationDateFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        Instant expiresAt = jwt.getExpiresAt();
        if (expiresAt == null) {
            throw new IllegalStateException("Token nao possui informacao de expiracao");
        }
        return expiresAt.toEpochMilli();
    }

    public boolean isTokenValid(String token) {
        return validateToken(token);
    }

    public AuthenticatedUser extractUser(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String subject = jwt.getSubject();
        String name = jwt.getClaimAsString("name");
        String email = jwt.getClaimAsString("email");
        Boolean emailVerified = jwt.getClaim("email_verified");
        List<String> scopes = resolveScopes(jwt);

        return new AuthenticatedUser(subject, name, email, Boolean.TRUE.equals(emailVerified), scopes);
    }

    private List<String> resolveScopes(Jwt jwt) {
        List<String> scopes = jwt.getClaimAsStringList("scope");
        if (scopes != null && !scopes.isEmpty()) {
            return scopes;
        }

        String scopeString = jwt.getClaimAsString("scope");
        if (scopeString != null && !scopeString.isBlank()) {
            return List.of(scopeString.split(" "));
        }

        List<String> scp = jwt.getClaimAsStringList("scp");
        if (scp != null && !scp.isEmpty()) {
            return scp;
        }

        return Collections.emptyList();
    }
}