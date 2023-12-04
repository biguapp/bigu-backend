package com.api.bigu.dto.address;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddress(AddressRequest addressRequest) {
        return Address.builder()
                .postalCode(addressRequest.getPostalCode())
                .nickname(addressRequest.getNickname())
                .state(addressRequest.getState())
                .city(addressRequest.getCity())
                .district(addressRequest.getDistrict())
                .street(addressRequest.getStreet())
                .number(addressRequest.getNumber())
                .complement(addressRequest.getComplement())
                .build();
    }

    public AddressResponse toAddressResponse(Address addressCreated) {
        return AddressResponse.builder()
                .id(addressCreated.getAddressId())
                .nickname(addressCreated.getNickname())
                .postalCode(addressCreated.getPostalCode())
                .state(addressCreated.getState())
                .city(addressCreated.getCity())
                .district(addressCreated.getDistrict())
                .street(addressCreated.getStreet())
                .number(addressCreated.getNumber())
                .complement(addressCreated.getComplement())
                .build();

    }
}
