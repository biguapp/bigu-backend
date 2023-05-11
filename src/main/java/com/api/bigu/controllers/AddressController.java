package com.api.bigu.controllers;

import com.api.bigu.dto.address.AddressDTO;
import com.api.bigu.dto.user.UserDTO;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.User;
import com.api.bigu.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/addressId")
    public ResponseEntity<AddressDTO> searchById(@PathVariable Integer addressId){
        try {
            AddressDTO address = addressService.getAddressById(addressId);
            return ResponseEntity.ok(address);
        } catch (AddressNotFoundException e){
            // tratar o caso em que o endereço não é encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado", e);
        } catch (Exception e) {
            // tratar outros tipos de exceção
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
        }
    }

    @GetMapping("/{addressCEP}")
    public ResponseEntity<AddressDTO> searchByCEP(@PathVariable Long cep) {

        try {
            AddressDTO address = addressService.getAddressByCEP(cep);
            return ResponseEntity.ok(address);

        } catch (AddressNotFoundException e) {
            // tratar o caso em que o endereço não é encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado", e);
        } catch (Exception e) {
            // tratar outros tipos de exceção
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
        }
    }

}
