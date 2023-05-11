package com.api.bigu.exceptions;

public class AddressNotFoundException extends Throwable {
    public AddressNotFoundException(String message) {
        super();
    }

    public String getMessage() {
        return "Endereço não encontrado.";
    }
}
