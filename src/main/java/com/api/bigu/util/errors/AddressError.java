/**
package com.api.bigu.util.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AddressError {

    static final String ADDRESS_NOT_FOUND = "O endereço não foi encontrado.";

    public static ResponseEntity<?> addressNotFoundError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(AddressError.ADDRESS_NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
}
*/
