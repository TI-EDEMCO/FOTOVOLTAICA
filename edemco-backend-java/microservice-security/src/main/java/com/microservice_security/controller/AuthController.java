package com.microservice_security.controller;

import com.microservice_security.dto.AuthRequest;
import com.microservice_security.dto.TokenDTO;
import com.microservice_security.entity.RefreshToken;
import com.microservice_security.entity.UserCredential;
import com.microservice_security.exceptions.ExpiredRefreshTokenException;
import com.microservice_security.exceptions.BadUserCredentialsException;
import com.microservice_security.service.AuthService;
import com.microservice_security.service.JwtService;
import com.microservice_security.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userCredential objeto con las credenciales del usuario.
     * @return un DTO con los tokens de acceso y refresh generados.
     * @throws Exception si ocurre algún error durante el registro.
     */
    @PostMapping("/register")
    public TokenDTO addNewUser(@RequestBody UserCredential userCredential) throws Exception {
        return authService.saveUser(userCredential);
    }

    /**
     * Autentica a un usuario y genera tokens de acceso y refresh.
     *
     * @param authRequest objeto con el nombre de usuario y contraseña.
     * @return un DTO con los tokens de acceso y refresh generados.
     * @throws BadUserCredentialsException si las credenciales proporcionadas son incorrectas.
     */
    @PostMapping("/login")
    public TokenDTO getToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByUsername(authRequest.getUsername());
            refreshTokenOptional.ifPresent(refreshToken -> refreshTokenService.DeleteRefreshToken(refreshToken));
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return TokenDTO
                    .builder()
                    .accessToken(authService.generateToken(authRequest.getUsername(), authRequest.getPassword()))
                    .refreshToken(refreshToken.getToken())
                    .build();
        } catch (Exception e) {
            throw new BadUserCredentialsException("Usuario y/o contraseña incorrectas");
        }
    }

    /**
     * Genera un nuevo token de acceso usando un token de refresh válido.
     *
     * @param tokenDTO objeto que contiene los tokens de acceso y refresh.
     * @return un DTO con el nuevo token de acceso y el token de refresh existente.
     * @throws ExpiredRefreshTokenException si el token de refresh no es válido o ha expirado.
     */
    @PostMapping("/refreshToken")
    public TokenDTO refreshToken(@RequestBody TokenDTO tokenDTO) {
        return refreshTokenService.findByToken(tokenDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserCredential)
                .map(userCredential -> {
                    String password = jwtService.decodeToken(tokenDTO.getAccessToken());
                    String accessToken = jwtService.generateToken(userCredential.getUsername(), password);
                    return TokenDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(tokenDTO.getRefreshToken()).build();
                }).orElseThrow(() -> new ExpiredRefreshTokenException("El refresh token no se encuentra en la base de datos"));
    }

    /**
     * Valida un token JWT proporcionado.
     *
     * @param token el token que se desea validar.
     * @return un mensaje indicando si el token es válido.
     */
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
