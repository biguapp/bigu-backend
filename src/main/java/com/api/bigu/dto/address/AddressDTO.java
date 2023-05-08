package com.api.bigu.dto.address;

import com.api.bigu.models.Address;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

public class AddressDTO {


    public Long postalCode;

    public String state;

    public String city;

    public String district;

    public String street;

    public String number;

    public String complement;

    public AddressDTO(Address address){
        this.postalCode = address.getPostalCode();
        this.state = address.getState();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.street = address.getStreet();
        this.number = address.getNumber();
        if (!address.getComplement().isEmpty()){
            this.complement = address.getComplement();
        }
    }

    public AddressDTO(Optional<Address> address) {
    }
}
