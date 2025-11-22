package com.example.api.adapter.input.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.adapter.input.api.response.HelloResponse;
import com.example.api.adapter.input.api.response.mappers.HelloResponseMapper;
import com.example.api.adapter.input.api.resquest.HelloRequest;
import com.example.api.adapter.input.api.resquest.mappers.HelloRequestMapper;
import com.example.api.domain.hello.usecase.HelloUseCase;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloRequestMapper REQUEST_MAPPER;

    @Autowired
    private HelloResponseMapper RESPONSE_MAPPER;

    private final HelloUseCase helloUseCase;

    public HelloController(HelloUseCase helloUseCase) {
        this.helloUseCase = helloUseCase;
    }
    
    @GetMapping
    public ResponseEntity<String> get() {
        var result = helloUseCase.getHelloMessage();
        if(result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    public ResponseEntity<HelloResponse> save(@Valid @RequestBody HelloRequest request) {
        var result = helloUseCase.createHello(REQUEST_MAPPER.toDomain(request));
        if(result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(RESPONSE_MAPPER.toResponse(result));
    }
    
}
