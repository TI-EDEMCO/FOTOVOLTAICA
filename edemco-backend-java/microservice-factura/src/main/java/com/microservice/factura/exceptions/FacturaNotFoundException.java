package com.microservice.factura.exceptions;

/**
 * Excepción personalizada para manejar casos en los que no se encuentra una factura.
 */
public class FacturaNotFoundException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param message Mensaje descriptivo del error.
     */
    public FacturaNotFoundException(String message) {
        super(message);
    }
}
