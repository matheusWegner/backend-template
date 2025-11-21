package com.example.api.infraestructure.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilterToken extends OncePerRequestFilter {

    private final JwtTokenProvider tokenService;

    public AuthenticationFilterToken(JwtTokenProvider tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bearer token ausente ou invalido");
            return;
        }

        boolean isValid = tokenService.isTokenValid(token);
        if (!isValid) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
            return;
        }

        setAuthenticationContext(token);

        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }


    private void setAuthenticationContext(String token) {
        var user = tokenService.extractUser(token);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(
                user.getScopes().stream()
                        .filter(scope -> scope != null && !scope.isBlank())
                        .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                        .collect(Collectors.toList())
        );

        var authentication = new PreAuthenticatedAuthenticationToken(user, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}