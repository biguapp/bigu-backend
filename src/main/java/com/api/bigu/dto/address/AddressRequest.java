package com.api.bigu.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NonNull
    @Schema(example = "Casa")
    private String nickname;

    @NonNull
    private String postalCode;

    @NonNull
    @Schema(example = "PB")
    private String state;

    @NonNull
    @Schema(example = "Campina Grande")
    private String city;

    @NonNull
    @Schema(example = "Centro")
    private String district;

    @NonNull
    @Schema(example = "Rua Exemplo")
    private String street;

    @Schema(example = "123")
    @NonNull
    private String number;

    @Schema(example = "Complemento")
    private String complement;
}
