package com.Operadores.Exceptions;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptions.class);

    /**
     * Maneja excepciones de tipo ResourceNotFoundException.
     *
     * @param rnfe Excepción lanzada cuando un recurso no es encontrado.
     * @return ResponseEntity con el mensaje de error y un estado HTTP 404 (NOT_FOUND).
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> tratamientoResourceNotFoundException(
            ResourceNotFoundException rnfe
    ) {
        LOGGER.error("Ocurrió un error: " + rnfe.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
    }

    /**
     * Maneja excepciones de tipo NullPointerException.
     *
     * @param npe Excepción lanzada cuando se intenta acceder a un objeto nulo.
     * @return ResponseEntity con el mensaje de error y un estado HTTP 204 (NO_CONTENT).
     */
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<String> tratamientoNullPointerException(NullPointerException npe) {
        LOGGER.error("Ocurrió un error: " + npe.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(npe.getMessage());
    }
}
