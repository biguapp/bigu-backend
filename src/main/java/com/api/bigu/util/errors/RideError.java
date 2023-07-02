/**
package com.api.bigu.util.errors;

import com.api.bigu.dto.ride.RideResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RideError {
    static final String RIDE_NOT_FOUND = "Carona não encontrada";
    static final String RIDE_IS_FULL = "Carona está cheia";
    static final String DATETIME_PASSED = "Data ou hora selecionada passou.";

    public static ResponseEntity<?> rideNotFoundError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(RideError.RIDE_NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> invalidDateTimeError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(RideError.DATETIME_PASSED),
                HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<?> rideIsFullError(){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(RideError.RIDE_IS_FULL),
                HttpStatus.INSUFFICIENT_STORAGE);
    }
}
*/
