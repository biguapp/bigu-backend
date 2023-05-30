package com.api.bigu.services;

import com.api.bigu.dto.address.AddressDTO;
import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.User;
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
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper addressMapper;

    public List<Address> getAllAddresses(){ return addressRepository.findAll(); }

    public AddressResponse getAddressByCEP(Long cep) throws AddressNotFoundException {
        Address address = addressRepository.findByPostalCode(cep).get();
        if (addressRepository.findByPostalCode(cep).isPresent()){
            return addressMapper.toAddressResponse(address);
        } else {
            throw new AddressNotFoundException("O endereço com CEP " + cep + " não existe.");
        }
    }

    public AddressResponse getAddressById(Integer addressId) throws AddressNotFoundException {
        Address address = addressRepository.findById(addressId).get();
        if (addressRepository.findById(addressId).isPresent()){
            return addressMapper.toAddressResponse(address);
        } else {
            throw new AddressNotFoundException("O endereço com Id " + addressId + " não existe.");
        }
    }

    public List<Address> getAddressesByUserId(Integer userId) throws UserNotFoundException, AddressNotFoundException {
        User user = userService.findUserById(userId);
        if (user.getAddresses().isEmpty()){
            throw new AddressNotFoundException("O usuário não possui endereços cadastrados.");
        }
        List<Address> addresses = user.getAddresses().values().stream().toList();
        return addresses;
    }

    public AddressResponse getAddressByNickname(String nickname, Integer userId) throws AddressNotFoundException {
        List<Address> addresses = addressRepository.findAll();
        for (Address address: addresses
             ) {
            if ((address.getNickname() == nickname) && (address.getUserId() == userId)) return addressMapper.toAddressResponse(address);
        }
        throw new AddressNotFoundException("O usuário não tem um endereço '" + nickname + "' cadastrado.");
    }


    public AddressResponse createAddress(AddressRequest addressRequest) {
       Address newAddress = addressMapper.toAddress(addressRequest);

       Address addressCreated = addressRepository.save(newAddress);

       return addressMapper.toAddressResponse(addressCreated);
    }
}
