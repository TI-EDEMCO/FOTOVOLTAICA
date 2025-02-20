package com.microservice_remitentes.service;

import com.microservice_remitentes.entities.Email;
import com.microservice_remitentes.exceptions.EmailConflictException;
import com.microservice_remitentes.exceptions.EmailNotFoundException;
import com.microservice_remitentes.persistence.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de correos electrónicos.
 */
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private EmailRepository emailRepository;

    /**
     * Obtiene todos los correos electrónicos registrados.
     * @return Optional con la lista de todos los correos electrónicos.
     */
    @Override
    public Optional<List<Email>> findAll() {
        return Optional.of(emailRepository.findAll());
    }

    /**
     * Crea un nuevo correo electrónico si no existe un conflicto con otro correo en la misma planta.
     * @param email Objeto Email a registrar.
     * @return Optional con el ID del correo creado.
     * @throws EmailConflictException Si ya existe un correo con el mismo email en la misma planta.
     */
    @Override
    public Optional<Long> createEmail(Email email) throws EmailConflictException {
        List<Email> emailList = emailRepository.findByIdPlantaAndEmail(email.getIdPlanta(), email.getEmail());
        if (!emailList.isEmpty()) {
            throw new EmailConflictException("El email " + email.getEmail() + " ya existe para la planta " + email.getIdPlanta());
        }
        emailRepository.save(email);
        return Optional.ofNullable(email.getIdEmail());
    }

    /**
     * Actualiza un correo electrónico existente.
     * @param email Objeto Email con los datos actualizados.
     * @return Optional con el correo electrónico actualizado.
     * @throws EmailNotFoundException Si el correo electrónico no se encuentra.
     */
    @Override
    public Optional<Email> updateEmail(Email email) throws EmailNotFoundException {
        Email editEmail = emailRepository.findById(String.valueOf(email.getIdEmail()))
                .orElseThrow(() -> new EmailNotFoundException("Email no encontrado"));
        editEmail.setEmail(email.getEmail());
        editEmail.setIdPlanta(email.getIdPlanta());
        emailRepository.save(editEmail);
        return Optional.of(editEmail);
    }

    /**
     * Elimina un correo electrónico por su ID.
     * @param idEmail ID del correo electrónico a eliminar.
     * @return Optional con un mensaje de confirmación de eliminación.
     * @throws EmailNotFoundException Si el correo electrónico no se encuentra.
     */
    @Override
    public Optional<String> deleteEmail(Long idEmail) throws EmailNotFoundException {
        emailRepository.findById(idEmail.toString())
                .orElseThrow(() -> new EmailNotFoundException("Email no encontrado"));
        emailRepository.deleteById(idEmail.toString());
        return Optional.of("Email eliminado con éxito");
    }

    /**
     * Obtiene los correos electrónicos relacionados con una planta específica.
     * @param idPlanta ID de la planta para filtrar los correos.
     * @return Optional con la lista de correos electrónicos relacionados.
     * @throws EmailNotFoundException Si no se encuentran correos electrónicos relacionados con la planta.
     */
    @Override
    public Optional<List<Email>> findByIdPlanta(String idPlanta) throws EmailNotFoundException {
        List<Email> emailList = emailRepository.findByIdPlanta(idPlanta);
        if (emailList.isEmpty()) {
            throw new EmailNotFoundException("Email no encontrado");
        }
        return Optional.ofNullable(emailRepository.findByIdPlanta(idPlanta));
    }
}
