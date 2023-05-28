package com.api.bigu.util.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RideError {
    static final String RIDE_NOT_FOUND = "Carona não encontrada";

    public static ResponseEntity<?> rideNotFoundError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(RideError.RIDE_NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
}
