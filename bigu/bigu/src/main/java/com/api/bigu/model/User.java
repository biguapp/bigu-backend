package com.api.bigu.model;

import java.util.*;

public class User{
    protected double matricula;

    protected String name;

    protected String email;

    protected String senha;

    public User(double matricula, String name, String email, String senha) {
        this.matricula = matricula;
        this.name = name;
        this.email = email;
        this.senha = senha;
    }
}