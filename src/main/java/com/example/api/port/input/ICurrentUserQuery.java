package com.example.api.port.input;

import java.util.Optional;

import com.example.api.domain.auth.model.AuthenticatedUser;

public interface ICurrentUserQuery {
    Optional<AuthenticatedUser> getCurrentUser();
}
