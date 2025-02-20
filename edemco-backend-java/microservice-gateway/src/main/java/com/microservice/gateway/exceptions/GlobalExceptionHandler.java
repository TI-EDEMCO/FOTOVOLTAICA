package com.microservice.gateway.exceptions;

import com.microservice.gateway.exceptions.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorMessage> handleUnauthorizationException(UnauthorizedAccessException ex) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED,HttpStatus.UNAUTHORIZED.value() ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
