package com.example.api.adapter.input.api.response.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.api.adapter.input.api.response.HelloResponse;
import com.example.api.domain.hello.entity.Hello;

@Mapper(componentModel = "spring")
public interface HelloResponseMapper {
    
    HelloResponseMapper INSTANCE = Mappers.getMapper(HelloResponseMapper.class);

    HelloResponse toResponse(Hello hello);
}
