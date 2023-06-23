package com.api.bigu.util.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CarError {
    static final String CAR_NOT_FOUND = "Carro não encontrado";

    static final String NO_CARS_FOUND = "O usuário não possui carros cadastrados";

    public static ResponseEntity<?> carNotFoundError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(CarError.CAR_NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> noCarsFoundError() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(CarError.NO_CARS_FOUND),
                HttpStatus.BAD_REQUEST);
    }
}
