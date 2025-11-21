package com.example.api.domain.auth.model;

import java.util.Collections;
import java.util.List;

public class AuthenticatedUser {

    private final String subject;
    private final String name;
    private final String email;
    private final boolean emailVerified;
    private final List<String> scopes;

    public AuthenticatedUser(String subject, String name, String email, boolean emailVerified, List<String> scopes) {
        this.subject = subject;
        this.name = name;
        this.email = email;
        this.emailVerified = emailVerified;
        this.scopes = scopes == null ? Collections.emptyList() : List.copyOf(scopes);
    }

    public String getSubject() {
        return subject;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public List<String> getScopes() {
        return scopes;
    }
}
