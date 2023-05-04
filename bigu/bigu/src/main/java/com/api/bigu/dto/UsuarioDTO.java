package com.api.bigu.dto;

public class UsuarioDTO {

    private Long matricula;
    private String nome;
    private String email;

    private String senha;
    private String photoUrl;

    public UsuarioDTO(Long matricula, String nome, String email, String senha) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
