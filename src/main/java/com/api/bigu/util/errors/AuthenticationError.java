package com.api.bigu.util.errors;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthenticationError {
    static final String UNAUTHORIZED = "NÃO AUTORIZADO: ";
    static final String FAILED_LOGOUT = "Não foi possível fazer logout: ";
    static final String WRONG_PASSWORD = "Senha incorreta.";

    public static ResponseEntity<?> wrongPassword() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(AuthenticationError.WRONG_PASSWORD)),
                HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<?> userUnauthorized(String message) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(AuthenticationError.UNAUTHORIZED, message)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> failedLogout(String message) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(AuthenticationError.FAILED_LOGOUT, message)),
                HttpStatus.NOT_FOUND);
    }
}
