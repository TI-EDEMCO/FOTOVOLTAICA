package com.microservice_remitentes.exceptions;

/**
 * Excepción personalizada que se lanza cuando ocurre un conflicto relacionado con correos electrónicos.
 */
public class EmailConflictException extends Exception {

    /**
     * Constructor que recibe un mensaje descriptivo del conflicto.
     * @param message Mensaje que describe el conflicto.
     */
    public EmailConflictException(String message) {
        super(message);
    }
}
