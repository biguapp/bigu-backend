package com.api.bigu.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressResponse {

    private Long postalCode;

    private String state;

    private String city;

    private String district;

    private String street;

    private String number;

    private String complement;
}
