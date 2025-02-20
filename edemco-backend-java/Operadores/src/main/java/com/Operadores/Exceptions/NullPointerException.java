package com.Operadores.Exceptions;

/**
 * Excepción personalizada para manejar casos de punteros nulos.
 *
 * Extiende la clase Exception para permitir la personalización de mensajes
 * relacionados con errores de NullPointerException.
 */
public class NullPointerException extends Exception {

    /**
     * Constructor que inicializa la excepción con un mensaje específico.
     *
     * @param mensaje Mensaje que describe el error de puntero nulo.
     */
    public NullPointerException(String mensaje) {
        super(mensaje);
    }
}
