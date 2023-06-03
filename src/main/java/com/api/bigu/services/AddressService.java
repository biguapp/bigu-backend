package com.api.bigu.services;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.User;
import com.api.bigu.repositories.AddressRepository;
import com.api.bigu.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AddressService {

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AddressMapper addressMapper;

    public List<AddressResponse> getAllCollegeAddresses() throws AddressNotFoundException {
        List<AddressResponse> collegeAddresses = new ArrayList<>();
        for (Address address: getAllAddresses()) {
            if (address.getNickname().contains("UFCG")){
                collegeAddresses.add(addressMapper.toAddressResponse(address));
            }
        }
        return collegeAddresses;
    }

    public List<Address> getAllAddresses(){ return addressRepository.findAll(); }

    public AddressResponse getAddressByCEP(String cep) throws AddressNotFoundException {
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

    public List<AddressResponse> getAddressesByUserId(Integer userId) throws UserNotFoundException, AddressNotFoundException {
        List<Address> addresses = userService.findUserById(userId).getAddresses().values().stream().toList();
        List<AddressResponse> addressesResponse = new ArrayList<>();
        for (Address address: addresses) {
            addressesResponse.add(addressMapper.toAddressResponse(address));
        }
        return addressesResponse;
    }

    public AddressResponse getAddressByNickname(String nickname, Integer userId) throws AddressNotFoundException {
        List<Address> addresses = addressRepository.findAll();
        for (Address address: addresses
             ) {
            if ((address.getNickname() == nickname) && (address.getUserId() == userId)) return addressMapper.toAddressResponse(address);
        }
        throw new AddressNotFoundException("O usuário não tem um endereço '" + nickname + "' cadastrado.");
    }


    public AddressResponse createAddress(Integer userId, AddressRequest address) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        Address newAddress = addressMapper.toAddress(address);
        newAddress.setUserId(userId);

        user.getAddresses().put(newAddress.getNickname(), newAddress);

        Address addressCreated = addressRepository.save(newAddress);
        userRepository.save(user);
        System.out.println(userRepository.findAll());
        return addressMapper.toAddressResponse(addressCreated);
    }
}
