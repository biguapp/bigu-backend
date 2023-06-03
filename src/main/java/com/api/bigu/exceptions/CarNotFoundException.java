package com.api.bigu.exceptions;

public class CarNotFoundException extends Throwable{
    public CarNotFoundException(String message) {
        super();
    }

    public String getMessage() {
        return "Endereço não encontrado.";
    }
}
