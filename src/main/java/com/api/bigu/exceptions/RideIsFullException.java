package com.api.bigu.exceptions;

public class RideIsFullException extends Throwable {
    public RideIsFullException(String message) {
        super();
    }

    public String getMessage() {
        return "A carona está cheia.";
    }
}
