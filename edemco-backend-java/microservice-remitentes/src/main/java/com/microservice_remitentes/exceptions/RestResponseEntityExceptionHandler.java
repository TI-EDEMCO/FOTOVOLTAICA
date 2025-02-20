package com.microservice_remitentes.exceptions;

import com.microservice_remitentes.exceptions.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Clase global para manejar excepciones en los controladores mediante la anotación @ControllerAdvice.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Maneja excepciones del tipo EmailNotFoundException.
     * @param exception Excepción lanzada cuando no se encuentra un correo electrónico.
     * @return ResponseEntity con un objeto ErrorMessage que describe el error y el estado HTTP 404.
     */
    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> remitenteNotFoundException(EmailNotFoundException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    /**
     * Maneja excepciones del tipo EmailConflictException.
     * @param exception Excepción lanzada cuando ocurre un conflicto relacionado con correos electrónicos.
     * @return ResponseEntity con un objeto ErrorMessage que describe el error y el estado HTTP 409.
     */
    @ExceptionHandler(EmailConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> remitenteConflictException(EmailConflictException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
