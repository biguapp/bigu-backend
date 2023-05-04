package com.api.bigu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Endereco {

    @Id
    private Long cep;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String num;

    private String complemento;

    @ManyToOne
    private Usuario usuario;

    public Endereco(Long cep, String estado, String cidade, String bairro, String rua, String num) {
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.num = num;
        this.complemento = "";
    }

    public Endereco(Long cep, String estado, String cidade, String bairro, String rua, String num, String complemento) {
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.num = num;
        this.complemento = complemento;
    }

    public Endereco() {
    }

    public String toString(){
        String saida = this.getRua() + ", " + this.getNum() + "\n" +
                this.getBairro() + ", " + this.getCidade() + ", " + this.getEstado() + "\n" +
                "CEP: " + this.getCep();
                if (this.complemento != "") {
                    saida += "\nComplemento : " + this.complemento;
                }
                return saida;
    }

    public Long getCep() {
        return cep;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
