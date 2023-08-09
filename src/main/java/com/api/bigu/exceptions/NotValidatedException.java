package com.api.bigu.exceptions;

import java.io.Serial;

public class NotValidatedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotValidatedException(String message) {
        super(message);
    }
}
