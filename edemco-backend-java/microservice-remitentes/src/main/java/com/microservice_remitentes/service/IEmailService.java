package com.microservice_remitentes.service;

import com.microservice_remitentes.entities.Email;
import com.microservice_remitentes.exceptions.EmailConflictException;
import com.microservice_remitentes.exceptions.EmailNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos del servicio para la gestión de correos electrónicos.
 */
public interface IEmailService {

    /**
     * Obtiene todos los correos electrónicos registrados.
     * @return Optional con la lista de todos los correos electrónicos.
     */
    Optional<List<Email>> findAll();

    /**
     * Crea un nuevo correo electrónico.
     * @param email Objeto Email a registrar.
     * @return Optional con el ID del correo creado.
     * @throws EmailConflictException Si ya existe un correo con el mismo email en la misma planta.
     */
    Optional<Long> createEmail(Email email) throws EmailConflictException;

    /**
     * Actualiza un correo electrónico existente.
     * @param email Objeto Email con los datos actualizados.
     * @return Optional con el correo electrónico actualizado.
     * @throws EmailNotFoundException Si el correo electrónico no se encuentra.
     */
    Optional<Email> updateEmail(Email email) throws EmailNotFoundException;

    /**
     * Elimina un correo electrónico por su ID.
     * @param idEmail ID del correo electrónico a eliminar.
     * @return Optional con un mensaje de confirmación de eliminación.
     * @throws EmailNotFoundException Si el correo electrónico no se encuentra.
     */
    Optional<String> deleteEmail(Long idEmail) throws EmailNotFoundException;

    /**
     * Obtiene los correos electrónicos relacionados con una planta específica.
     * @param idPlanta ID de la planta para filtrar los correos.
     * @return Optional con la lista de correos electrónicos relacionados.
     * @throws EmailNotFoundException Si no se encuentran correos electrónicos relacionados con la planta.
     */
    Optional<List<Email>> findByIdPlanta(String idPlanta) throws EmailNotFoundException;
}
