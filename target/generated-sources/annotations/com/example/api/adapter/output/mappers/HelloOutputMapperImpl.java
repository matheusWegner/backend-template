package com.example.api.adapter.output.mappers;

import com.example.api.adapter.output.bd.HelloEntity;
import com.example.api.domain.hello.entity.Hello;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T22:43:45-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class HelloOutputMapperImpl implements HelloOutputMapper {

    @Override
    public HelloEntity toEntity(Hello hello) {
        if ( hello == null ) {
            return null;
        }

        HelloEntity helloEntity = new HelloEntity();

        helloEntity.setId( hello.getId() );
        helloEntity.setMessage( hello.getMessage() );

        return helloEntity;
    }

    @Override
    public Hello toDomain(HelloEntity helloEntity) {
        if ( helloEntity == null ) {
            return null;
        }

        Hello hello = new Hello();

        hello.setId( helloEntity.getId() );
        hello.setMessage( helloEntity.getMessage() );

        return hello;
    }
}
