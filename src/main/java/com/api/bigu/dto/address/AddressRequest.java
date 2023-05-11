package com.api.bigu.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NonNull
    private Long postalCode;
    @NonNull
    private String state;
    @NonNull
    private String city;
    @NonNull
    private String district;
    @NonNull
    private String street;
    @NonNull
    private String number;

    private String complement;
}
