/**
package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.user.User;
import com.api.bigu.services.AddressService;
import com.api.bigu.user.UserService;
import com.api.bigu.util.errors.AddressError;
import com.api.bigu.user.UserError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping("/get-ufcg")
    public ResponseEntity<?> getUfcgAddresses(@RequestHeader("Authorization") String authorizationHeader) {
        List<AddressResponse> ufcgAddresses = new ArrayList<>();
        try {
            Integer adminId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User admin = userService.findUserById(adminId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), admin)){
                ufcgAddresses = addressService.getAllCollegeAddresses();
            }
            return ResponseEntity.ok(ufcgAddresses);
        } catch (AddressNotFoundException e) {
            return AddressError.addressNotFoundError();
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        }
    }

    @GetMapping("/get-all")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{addressId}")
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
        List<AddressResponse> addresses = new ArrayList<>();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), userService.findUserById(userId))){
                addresses = addressService.getAddressesByUserId(userId);
            }
            return ResponseEntity.ok(addresses);
        } catch (AddressNotFoundException e){
            return AddressError.addressNotFoundError();
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }


    @GetMapping("/cep/{cep}")
    public ResponseEntity<AddressResponse> searchByCEP(@PathVariable String cep) {

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
    public ResponseEntity<?> createAddress(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid AddressRequest address) throws UserNotFoundException {
        try{
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            AddressResponse createdAddress = addressService.createAddress(userId, address);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Integer addressId) throws UserNotFoundException {
        try{
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            addressService.removeAddressFromUser(userId, addressId);
        } catch (AddressNotFoundException e) {
            return AddressError.addressNotFoundError();
        }
        return ResponseEntity.ok("Endereço removido.");
    }

}
*/
