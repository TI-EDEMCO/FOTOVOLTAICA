package com.microservice.facturacion_especial.exceptions;

/**
 * Excepción personalizada para manejar errores relacionados con la facturación especial.
 * Extiende de la clase base {@link Exception}.
 */
public class FacturacionEspecialException extends Exception {

    /**
     * Constructor que permite crear una excepción con un mensaje personalizado.
     *
     * @param message Mensaje descriptivo del error.
     */
    public FacturacionEspecialException(String message) {
        super(message);
    }
}
