package com.api.bigu.model;

import com.api.bigu.models.Address;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Motorista motorista;

    private Address origem;

    private Address destino;

    private float distancia;

    private float preco;
}
