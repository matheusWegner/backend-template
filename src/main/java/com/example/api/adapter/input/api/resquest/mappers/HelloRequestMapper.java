package com.example.api.adapter.input.api.resquest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.api.adapter.input.api.resquest.HelloRequest;
import com.example.api.domain.hello.entity.Hello;

@Mapper(componentModel = "spring")
public interface HelloRequestMapper {
    HelloRequestMapper INSTANCE = Mappers.getMapper(HelloRequestMapper.class);

    Hello toDomain(HelloRequest helloRequest);
}
