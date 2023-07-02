/**
package com.api.bigu.util.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthError {

    static final String TOKEN_EXPIRED = "O token expirou.";

    public static ResponseEntity<?> tokenExpiredError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(AuthError.TOKEN_EXPIRED),
                HttpStatus.UNAUTHORIZED);
    }


}
*/
