package com.microservice_remitentes.exceptions;

/**
 * Excepción personalizada que se lanza cuando un correo electrónico no es encontrado.
 */
public class EmailNotFoundException extends Exception {

    /**
     * Constructor que recibe un mensaje descriptivo de la ausencia del correo electrónico.
     * @param message Mensaje que describe la excepción.
     */
    public EmailNotFoundException(String message) {
        super(message);
    }
}
