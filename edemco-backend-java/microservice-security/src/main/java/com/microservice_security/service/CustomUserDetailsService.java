package com.microservice_security.service;

import com.microservice_security.entity.UserCredential;
import com.microservice_security.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementación personalizada de UserDetailsService para cargar detalles de usuario desde la base de datos.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    /**
     * Carga los detalles de un usuario a partir de su nombre de usuario.
     *
     * @param username el nombre de usuario del usuario.
     * @return los detalles del usuario encapsulados en un objeto UserDetails.
     * @throws UsernameNotFoundException si el usuario no se encuentra en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        // Retorna un objeto User que implementa UserDetails
        return User.builder()
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .build();
    }
}
