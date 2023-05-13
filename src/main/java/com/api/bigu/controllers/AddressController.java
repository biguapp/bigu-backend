package com.api.bigu.controllers;

import com.api.bigu.dto.address.AddressDTO;
import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<AddressResponse> createNewAddress(@Valid @RequestBody AddressRequest addressRequest) {
        AddressResponse newAddress = addressService.createAddress(addressRequest);

        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

}
