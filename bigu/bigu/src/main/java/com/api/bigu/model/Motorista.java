package com.api.bigu.model;

import java.util.*;

public class Motorista extends Usuario{

    private String cnh;

    private Set<Carro> carros;

    public Motorista(Long matricula, String name, String email, String senha, Set<Carro> carros) {
        super(matricula, name, email, senha);
        this.carros = carros;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public Set<Carro> getCarros() {
        return carros;
    }

    public void setCarros(Set<Carro> carros) {
        this.carros = carros;
    }
}
