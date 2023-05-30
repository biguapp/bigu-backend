package com.api.bigu.util.errors;

import com.api.bigu.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserError {

    static final String USER_NOT_FOUND = "O usuário não foi encontrado.";
    static final String USER_BLOCKED = "O usuário foi bloqueado.";
    static final String NOT_AN_ADMIN = "Não é admin";

    public static ResponseEntity<?> userNotFoundError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(UserError.USER_NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> userBlockedError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(UserError.USER_BLOCKED),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> userNotAnAdministrator() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(UserError.NOT_AN_ADMIN),
                HttpStatus.UNAUTHORIZED);
    }
}
