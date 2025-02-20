package com.Operadores.Exceptions;

/**
 * Excepción personalizada para manejar casos donde un recurso no es encontrado.
 *
 * Extiende la clase Exception para permitir la personalización de mensajes
 * relacionados con errores de recursos no encontrados.
 */
public class ResourceNotFoundException extends Exception {

    /**
     * Constructor que inicializa la excepción con un mensaje específico.
     *
     * @param mensaje Mensaje que describe el error de recurso no encontrado.
     */
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
