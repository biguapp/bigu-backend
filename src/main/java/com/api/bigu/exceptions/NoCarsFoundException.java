package com.api.bigu.exceptions;

public class NoCarsFoundException extends Throwable{
    public NoCarsFoundException(String message) {
        super();
    }

    public String getMessage() {
        return "Nenhum carro encontrado.";
    }
}
