package com.api.bigu.model;

public class Usuario {
    protected double matricula;

    protected String name;

    protected String email;

    protected String senha;

    public Usuario(double matricula, String name, String email, String senha) {
        this.matricula = matricula;
        this.name = name;
        this.email = email;
        this.senha = senha;
    }
}