package com.api.bigu.exceptions;

public class RideNotFoundException extends Throwable {
    public RideNotFoundException(String message) {
        super(message);
    }

    public String getMessage() {
        return "Corrida não encontrada.";
    }
}
