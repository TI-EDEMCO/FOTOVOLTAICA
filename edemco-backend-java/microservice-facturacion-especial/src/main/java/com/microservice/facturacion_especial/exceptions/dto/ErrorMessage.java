package com.microservice.facturacion_especial.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Clase que representa un mensaje de error estándar para las excepciones.
 * Contiene información sobre el estado HTTP, código de estado y un mensaje descriptivo.
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
     * Código de estado numérico asociado al error.
     */
    private Integer statusCode;

    /**
     * Mensaje descriptivo del error.
     */
    private String message;
}
