package com.api.bigu.exceptions;

public class WrongPasswordException extends Throwable{
    public WrongPasswordException(String message) {
        super();
    }

    public String getMessage() {
        return "Senha errada.";
    }
}
