package com.microservice_remitentes.persistence;

import com.microservice_remitentes.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio JPA para la entidad Email. Proporciona métodos para realizar operaciones sobre la base de datos.
 */
public interface EmailRepository extends JpaRepository<Email, String> {

    /**
     * Busca todos los correos electrónicos asociados a una planta específica.
     * @param idPlanta ID de la planta para filtrar los correos electrónicos.
     * @return Lista de correos electrónicos relacionados con la planta.
     */
    @Query("SELECT e FROM Email e WHERE e.idPlanta = :idPlanta")
    List<Email> findByIdPlanta(String idPlanta);

    /**
     * Busca los correos electrónicos asociados a una planta específica y un email en particular.
     * @param idPlanta ID de la planta para filtrar los correos electrónicos.
     * @param email Dirección de correo electrónico para filtrar.
     * @return Lista de correos electrónicos que coinciden con el ID de planta y la dirección de correo.
     */
    @Query("SELECT e FROM Email e WHERE e.idPlanta = :idPlanta AND e.email = :email")
    List<Email> findByIdPlantaAndEmail(String idPlanta, String email);
}
