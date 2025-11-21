package com.example.api.infraestructure.exceptions;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                        request.getRequestURI(),
                        "Argumento inválido.",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        List.of()
                );

                return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
                String errorMessage = String.format("O argumento '%s' possui um valor inválido: '%s'.",
                        ex.getName(), ex.getValue());

                ExceptionResponse exceptionResponse = new ExceptionResponse(
                        request.getRequestURI(),
                        errorMessage,
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        List.of()
                );

                return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
                List<ExceptionResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors()
                        .stream()
                        .map(error -> new ExceptionResponse.FieldError(
                                error.getField(),
                                error.getDefaultMessage()))
                        .collect(Collectors.toList());

                ExceptionResponse exceptionResponse = new ExceptionResponse(
                        request.getRequestURI(),
                        "A validação falhou para um ou mais campos.",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        fieldErrors
                );

                return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
        
        
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(
                HttpMessageNotReadableException ex, HttpServletRequest request) {

                String detalhe = ex.getMostSpecificCause() != null 
                        ? ex.getMostSpecificCause().getMessage() 
                        : ex.getMessage();

                ExceptionResponse resp = new ExceptionResponse(
                        request.getRequestURI(),
                        "Corpo da requisição inválido ou malformado: " + detalhe,
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        List.of()
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        @ExceptionHandler(ConversionFailedException.class)
        public ResponseEntity<ExceptionResponse> handleConversionFailed(
                ConversionFailedException ex, HttpServletRequest request) {

                String valor = ex.getValue() != null ? ex.getValue().toString() : "null";
                String tipoDestino = ex.getTargetType() != null ? ex.getTargetType().toString() : "desconhecido";
                String msg = String.format("Falha ao converter o valor '%s' para o tipo '%s'.", valor, tipoDestino);

                ExceptionResponse resp = new ExceptionResponse(
                        request.getRequestURI(),
                        msg,
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        List.of()
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ExceptionResponse> handleMediaTypeNotSupported(
                HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {

                StringBuilder sb = new StringBuilder();
                sb.append("Tipo de mídia não suportado: ").append(ex.getContentType()).append(". ");
                sb.append("Tipos suportados: ");
                ex.getSupportedMediaTypes().forEach(mt -> sb.append(mt).append(", "));

                ExceptionResponse resp = new ExceptionResponse(
                        request.getRequestURI(),
                        sb.toString(),
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                        LocalDateTime.now(),
                        List.of()
                );
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(resp);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex , HttpServletRequest request) {
                ExceptionResponse error = new ExceptionResponse(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now(),
                        List.of()
                );
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
}