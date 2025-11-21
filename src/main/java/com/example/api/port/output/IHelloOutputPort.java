package com.example.api.port.output;

import com.example.api.domain.hello.entity.Hello;

public interface IHelloOutputPort {
    public Hello getHelloById(String id);
    public void saveHello(Hello hello);
}
