package com.microservice_security.repository;

import com.microservice_security.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para gestionar operaciones CRUD relacionadas con las credenciales de usuario.
 * Extiende JpaRepository para proporcionar métodos estándar y consultas personalizadas.
 */
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

    /**
     * Busca las credenciales de un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario.
     * @return un Optional que contiene las credenciales del usuario si se encuentran.
     */
    Optional<UserCredential> findByUsername(String username);
}
