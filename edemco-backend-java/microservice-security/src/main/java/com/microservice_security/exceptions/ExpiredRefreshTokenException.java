package com.microservice_security.exceptions;

/**
 * Excepción personalizada que se lanza cuando un token de refresco ha expirado.
 * Extiende RuntimeException para manejar errores en tiempo de ejecución.
 */
public class ExpiredRefreshTokenException extends RuntimeException {

    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * @param message mensaje que describe el motivo de la excepción.
     */
    public ExpiredRefreshTokenException(String message) {
        super(message);
    }
}
