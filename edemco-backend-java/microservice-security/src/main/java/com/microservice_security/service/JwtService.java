package com.microservice_security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la generación, validación y decodificación de tokens JWT.
 */
@Component
public class JwtService {

    @Value("${api.secret}")
    private String SECRET;

    /**
     * Decodifica un token JWT y extrae el campo "password" del payload.
     *
     * @param token el token JWT a decodificar.
     * @return la contraseña extraída del token.
     * @throws RuntimeException si ocurre un error al procesar el payload.
     */
    public String decodeToken(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = token.split("\\.");
        String payload = new String(decoder.decode(parts[1]));
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map claims = mapper.readValue(payload, Map.class);
            return (String) claims.get("password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT payload", e);
        }
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     *
     * @param token el token JWT.
     * @return la fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un reclamo específico del token JWT utilizando una función.
     *
     * @param token el token JWT.
     * @param claimsResolver la función para resolver el reclamo.
     * @param <T> el tipo del reclamo extraído.
     * @return el valor del reclamo.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los reclamos del token JWT.
     *
     * @param token el token JWT.
     * @return los reclamos contenidos en el token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica si un token JWT está expirado.
     *
     * @param token el token JWT.
     * @return true si el token está expirado, de lo contrario false.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valida un token JWT para asegurar que es correcto y no está manipulado.
     *
     * @param token el token JWT.
     */
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    /**
     * Genera un token JWT basado en el nombre de usuario y la contraseña.
     *
     * @param username el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return el token JWT generado.
     */
    public String generateToken(String username, String password) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("password", password);
        return createToke(claims, username);
    }

    /**
     * Crea un token JWT con los reclamos especificados y el nombre de usuario.
     *
     * @param claims los reclamos a incluir en el token.
     * @param username el nombre de usuario asociado al token.
     * @return el token JWT generado.
     */
    private String createToke(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2700000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene la clave de firma utilizada para firmar y validar los tokens JWT.
     *
     * @return la clave de firma.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
