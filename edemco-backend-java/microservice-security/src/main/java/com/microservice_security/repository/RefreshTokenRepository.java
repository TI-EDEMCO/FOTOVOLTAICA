package com.microservice_security.repository;

import com.microservice_security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar operaciones CRUD relacionadas con los tokens de refresco.
 * Extiende JpaRepository para proporcionar métodos estándar y consultas personalizadas.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Busca un token de refresco por su valor.
     *
     * @param token el valor del token de refresco.
     * @return un Optional que contiene el token de refresco si se encuentra.
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Busca un token de refresco asociado a un nombre de usuario.
     *
     * @param username el nombre de usuario asociado al token.
     * @return un Optional que contiene el token de refresco si se encuentra.
     */
    @Query("SELECT r FROM RefreshToken r INNER JOIN UserCredential u ON r.userCredential.idUser = u.idUser WHERE u.username = :username")
    Optional<RefreshToken> findByUsername(String username);
}
