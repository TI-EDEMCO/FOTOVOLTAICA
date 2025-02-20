package com.microservice_security.service;

import com.microservice_security.dto.TokenDTO;
import com.microservice_security.entity.UserCredential;
import com.microservice_security.exceptions.BadUserCredentialsException;
import com.microservice_security.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de autenticación y registro de usuarios.
 * Proporciona métodos para registrar usuarios, generar y validar tokens.
 */
@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userCredential objeto que contiene las credenciales del usuario.
     * @return un DTO que contiene los tokens de acceso y refresh generados.
     * @throws BadUserCredentialsException si el usuario ya existe.
     */
    public TokenDTO saveUser(UserCredential userCredential) throws BadUserCredentialsException {
        if (userCredentialRepository.findByUsername(userCredential.getUsername()).isPresent()) {
            throw new BadUserCredentialsException("Ya existe un usuario con este correo: " + userCredential.getUsername());
        }

        String decodedPassword = userCredential.getPassword();

        // Codifica la contraseña antes de guardarla
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);

        // Genera y retorna los tokens
        return TokenDTO.builder()
                .accessToken(jwtService.generateToken(userCredential.getUsername(), decodedPassword))
                .refreshToken(refreshTokenService.createRefreshToken(userCredential.getUsername()).getToken())
                .build();
    }

    /**
     * Genera un token JWT basado en el nombre de usuario y la contraseña.
     *
     * @param username el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return un token JWT.
     */
    public String generateToken(String username, String password) {
        return jwtService.generateToken(username, password);
    }

    /**
     * Valida un token JWT.
     *
     * @param token el token que se desea validar.
     */
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
