package com.microservice_security.exceptions;

/**
 * Excepción personalizada que se lanza cuando las credenciales de usuario son incorrectas.
 * Extiende RuntimeException para manejar errores en tiempo de ejecución.
 */
public class BadUserCredentialsException extends RuntimeException {

    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * @param message mensaje que describe el motivo de la excepción.
     */
    public BadUserCredentialsException(String message) {
        super(message);
    }
}
