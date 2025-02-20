package com.microservice.facturacion_especial.exceptions;

import com.microservice.facturacion_especial.exceptions.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase que maneja de forma global las excepciones en el microservicio.
 * Utiliza {@link ControllerAdvice} para capturar y manejar excepciones personalizadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo {@link FacturacionEspecialException}.
     * Devuelve un mensaje de error con detalles de la excepción en el cuerpo de la respuesta.
     *
     * @param ex Excepción lanzada.
     * @return {@link ResponseEntity} con el mensaje de error y estado HTTP.
     */
    @ExceptionHandler(FacturacionEspecialException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorMessage> handleFacturacionEspecialException(FacturacionEspecialException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
    }
}
