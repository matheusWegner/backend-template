package com.example.api.adapter.output.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.api.adapter.output.bd.HelloEntity;
import com.example.api.domain.hello.entity.Hello;

@Mapper(componentModel = "spring")
public interface HelloOutputMapper {
    HelloOutputMapper INSTANCE = Mappers.getMapper(HelloOutputMapper.class);

    HelloEntity toEntity(Hello hello);
    Hello toDomain(HelloEntity helloEntity);
}
