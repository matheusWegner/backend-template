package com.example.api.adapter.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.adapter.output.mappers.HelloOutputMapper;
import com.example.api.domain.hello.entity.Hello;
import com.example.api.port.output.IHelloOutputPort;
import com.example.api.port.output.bd.repository.HelloRepository;

@Service
public class HelloOutputPort implements IHelloOutputPort {

    @Autowired
    private HelloOutputMapper MAPPER;

    private final HelloRepository helloRepository;

    public HelloOutputPort(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }
   
    @Override
    public Hello getHelloById(String id) {
        var result = MAPPER.toDomain(helloRepository.findById(id).orElse(null));
        return result;
    }

    @Override
    public void saveHello(Hello hello) {
        var entity = MAPPER.toEntity(hello);
        helloRepository.save(entity);
    }
    
}