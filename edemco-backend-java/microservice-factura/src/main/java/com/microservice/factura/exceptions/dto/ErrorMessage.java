package com.microservice.factura.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    /**
     * Representa el estado HTTP asociado al error.
     */
    private HttpStatus status;

    /**
     * Mensaje descriptivo del error ocurrido.
     */
    private String message;
}
