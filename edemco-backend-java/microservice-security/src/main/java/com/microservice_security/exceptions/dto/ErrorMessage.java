package com.microservice_security.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Clase que representa un mensaje de error utilizado para manejar excepciones en la aplicación.
 * Contiene información sobre el estado HTTP, el código de estado y un mensaje descriptivo.
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
     * Código numérico del estado HTTP asociado al error.
     */
    private Integer statusCode;

    /**
     * Mensaje descriptivo del error.
     */
    private String message;
}
