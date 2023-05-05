package com.api.bigu.service;

import com.api.bigu.model.Motorista;
import com.api.bigu.repository.MotoristaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    //public Motorista criaMotorista(){}
}
