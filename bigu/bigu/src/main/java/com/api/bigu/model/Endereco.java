package com.api.bigu.model;

public class Endereco {

    private Long cep;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String num;

    private String complemento;

    public Endereco(Long cep, String estado, String cidade, String bairro, String rua, String num) {
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.num = num;
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


}
