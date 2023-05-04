package com.api.bigu.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Motorista motorista;

    private Endereco origem;


    private Endereco destino;

    private float distancia;

    private float preco;
}
