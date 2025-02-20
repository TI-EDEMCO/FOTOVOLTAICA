package com.microservice_security.service;

import com.microservice_security.entity.RefreshToken;
import com.microservice_security.exceptions.ExpiredRefreshTokenException;
import com.microservice_security.repository.RefreshTokenRepository;
import com.microservice_security.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para gestionar tokens de actualización (refresh tokens).
 * Proporciona métodos para crear, verificar y eliminar tokens de actualización.
 */
@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserCredentialRepository userRepository;

    /**
     * Crea un nuevo token de actualización para un usuario especificado.
     *
     * @param username el nombre de usuario para el cual se genera el token.
     * @return el token de actualización creado.
     */
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(userRepository.findByUsername(username).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1209600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Busca un token de actualización por su valor.
     *
     * @param token el valor del token de actualización.
     * @return un Optional que contiene el token de actualización si se encuentra.
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifica la expiración de un token de actualización.
     *
     * @param token el token de actualización a verificar.
     * @return el token si no ha expirado.
     * @throws ExpiredRefreshTokenException si el token ha expirado.
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException("El token de actualización " + token.getToken() + " está vencido. Inicia sesión de nuevo.");
        }
        return token;
    }

    /**
     * Busca un token de actualización asociado a un nombre de usuario.
     *
     * @param username el nombre de usuario.
     * @return un Optional que contiene el token de actualización si se encuentra.
     */
    public Optional<RefreshToken> findByUsername(String username) {
        return refreshTokenRepository.findByUsername(username);
    }

    /**
     * Elimina un token de actualización de la base de datos.
     *
     * @param refreshToken el token de actualización a eliminar.
     */
    public void DeleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
