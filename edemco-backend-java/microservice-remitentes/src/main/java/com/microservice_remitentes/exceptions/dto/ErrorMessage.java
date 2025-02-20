package com.microservice_remitentes.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Clase que representa un mensaje de error para manejar excepciones personalizadas.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    /**
     * Estado HTTP asociado al error.
     */
    private HttpStatus status;

    /**
     * Mensaje descriptivo del error.
     */
    private String message;

    /**
     * Código numérico específico del error.
     */
    private Integer code;
}
