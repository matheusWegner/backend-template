package com.example.api.adapter.input.api.response.mappers;

import com.example.api.adapter.input.api.response.HelloResponse;
import com.example.api.domain.hello.entity.Hello;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T22:43:45-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class HelloResponseMapperImpl implements HelloResponseMapper {

    @Override
    public HelloResponse toResponse(Hello hello) {
        if ( hello == null ) {
            return null;
        }

        HelloResponse helloResponse = new HelloResponse();

        helloResponse.setMessage( hello.getMessage() );

        return helloResponse;
    }
}
