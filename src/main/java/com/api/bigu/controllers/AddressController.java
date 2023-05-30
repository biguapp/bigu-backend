package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.address.AddressDTO;
import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.services.AddressService;
import com.api.bigu.util.errors.AddressError;
import com.api.bigu.util.errors.UserError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    JwtService jwtService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

//    @GetMapping("/")
//    public ResponseEntity<?> getAllAddressesOfUser(@RequestHeader("Authorization") String authorizationHeader) {
//        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
//        try {
//            return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
//        } catch (UserNotFoundException e) {
//            return UserError.userNotFoundError();
//        } catch (AddressNotFoundException e) {
//            return AddressError.addressNotFoundError();
//        }
//    }

    @GetMapping("/get-ufcg")
    public ResponseEntity<?> getUfcgAddresses(@RequestHeader("Authorization") String authorizationHeader) {
        Integer adminId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            return ResponseEntity.ok(addressService.getAddressesByUserId(adminId));
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (AddressNotFoundException e) {
            return AddressError.addressNotFoundError();
        }
    }

    @GetMapping("/get-all")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/addressId/{addressId}")
    public ResponseEntity<?> searchById(@PathVariable Integer addressId){
        try {
            AddressResponse address = addressService.getAddressById(addressId);
            return ResponseEntity.ok(address);
        } catch (AddressNotFoundException e){
            // tratar o caso em que o endereço não é encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado", e);
        } catch (Exception e) {
            // tratar outras categorias de exceção
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
        }
    }

    @GetMapping()
    public ResponseEntity<?> searchByUserId(@RequestHeader("Authorization") String authorizationHeader){
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            List<Address> addresses = addressService.getAddressesByUserId(userId);
            System.out.println(addresses);
            return ResponseEntity.ok(addresses);
        } catch (AddressNotFoundException e){
            return AddressError.addressNotFoundError();
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }


    @GetMapping("/addressCEP/{cep}")
    public ResponseEntity<AddressResponse> searchByCEP(@PathVariable Long cep) {

        try {
            AddressResponse address = addressService.getAddressByCEP(cep);
            return ResponseEntity.ok(address);

        } catch (AddressNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
        }
    }

    @PostMapping()
    public ResponseEntity<AddressResponse> createAddress(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid AddressRequest address) throws UserNotFoundException {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        AddressResponse createdAddress = addressService.createAddress(userId, address);

        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

}
