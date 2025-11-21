package com.example.api.adapter.input.api.resquest.mappers;

import com.example.api.adapter.input.api.resquest.HelloRequest;
import com.example.api.domain.hello.entity.Hello;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T22:43:45-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class HelloRequestMapperImpl implements HelloRequestMapper {

    @Override
    public Hello toDomain(HelloRequest helloRequest) {
        if ( helloRequest == null ) {
            return null;
        }

        Hello hello = new Hello();

        hello.setId( helloRequest.getId() );
        hello.setMessage( helloRequest.getMessage() );

        return hello;
    }
}
