package com.example.api.domain.hello.usecase;

import org.springframework.stereotype.Service;

import com.example.api.domain.auth.model.AuthenticatedUser;
import com.example.api.domain.hello.entity.Hello;
import com.example.api.port.input.ICurrentUserQuery;
import com.example.api.port.output.IHelloOutputPort;

@Service
public class HelloUseCase {
    
    private final IHelloOutputPort helloOutputPort;
    private final ICurrentUserQuery currentUserQuery;

    public HelloUseCase(IHelloOutputPort helloOutputPort, ICurrentUserQuery currentUserQuery) {
        this.helloOutputPort = helloOutputPort;
        this.currentUserQuery = currentUserQuery;
    }

    public String getHelloMessage() {
        return currentUserQuery.getCurrentUser()
                .map(this::buildPersonalizedGreeting)
                .orElse("Hello, guest user!");
    }

    public Hello createHello(Hello hello) {
        helloOutputPort.saveHello(hello);
        return hello;
    }

    private String buildPersonalizedGreeting(AuthenticatedUser user) {
        String displayName = user.getName() != null ? user.getName() : user.getEmail();
        if (displayName == null || displayName.isBlank()) {
            displayName = user.getSubject();
        }
        return "Hello, " + displayName + "!";
    }

}
