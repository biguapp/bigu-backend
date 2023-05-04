package com.api.bigu.model;

import jakarta.persistence.*;

import java.util.*;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
public class Usuario {
    @Id
    protected Long matricula;

    protected String name;

    protected String email;

    protected String senha;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

    private String photoUrl;

    public Usuario(){}

    public Usuario(Long matricula, String name, String email, String senha) {
        this.matricula = matricula;
        this.name = name;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(Long matricula, String name, String email, String senha, String photoUrl) {
        this.matricula = matricula;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.photoUrl = photoUrl;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}