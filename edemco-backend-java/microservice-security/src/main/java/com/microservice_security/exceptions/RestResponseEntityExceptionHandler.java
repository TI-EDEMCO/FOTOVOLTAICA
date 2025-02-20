package com.microservice_security.exceptions;

import com.microservice_security.exceptions.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controlador global de excepciones para manejar errores personalizados en la aplicación.
 * Extiende ResponseEntityExceptionHandler para aprovechar el manejo estándar de excepciones.
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Maneja las excepciones ExpiredRefreshTokenException.
     *
     * @param exception la excepción lanzada cuando un token de refresco ha expirado.
     * @return una respuesta con un mensaje de error y un estado HTTP.
     */
    @ExceptionHandler(ExpiredRefreshTokenException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorMessage> expiredRefreshTokenExceptionHandler(ExpiredRefreshTokenException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
    }

    /**
     * Maneja las excepciones BadUserCredentialsException.
     *
     * @param exception la excepción lanzada cuando las credenciales de usuario son incorrectas.
     * @return una respuesta con un mensaje de error y un estado HTTP.
     */
    @ExceptionHandler(BadUserCredentialsException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorMessage> userNotRegisterException(BadUserCredentialsException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
    }
}
