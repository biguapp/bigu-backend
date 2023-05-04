package com.api.bigu.model;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.*;

public class Usuario {
    protected double matricula;

    protected String name;

    protected String email;

    protected String senha;

    protected Set<Endereco> enderecos;

    @OneToOne
    private String photoUrl;

    public Usuario(){}

    public Usuario(double matricula, String name, String email, String senha, Map enderecos) {
        this.matricula = matricula;
        this.name = name;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(double matricula, String name, String email, String senha, Map enderecos, String photoUrl) {
        this.matricula = matricula;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.photoUrl = photoUrl;
    }


}