package com.example.api.infraestructure.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ExceptionResponse(
        String path,
        String message,
        int status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dateTime,
        List<FieldError> fieldErrors
) {
    public record FieldError(String field, String message) {
    }
}