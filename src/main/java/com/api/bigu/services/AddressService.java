package com.api.bigu.services;

import com.api.bigu.dto.address.AddressDTO;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAllAddresses(){ return addressRepository.findAll(); }

    public AddressDTO getAddressByCEP(Long cep) throws AddressNotFoundException {
        Optional<Address> address = addressRepository.findByPostalCode(cep);
        if (address.isPresent()){
            return new AddressDTO(address);
        } else {
            throw new AddressNotFoundException("O endereço com CEP " + cep + " não existe.");
        }
    }

    public AddressDTO getAddressById(Integer addressId) throws AddressNotFoundException {
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()){
            return new AddressDTO(address);
        } else {
            throw new AddressNotFoundException("O endereço com Id " + addressId + " não existe.");
        }
    }


}
