package com.api.bigu.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = "Nickname field cannot be null")
    @Schema(example = "Casa")
    private String nickname;

    @NotNull(message = "Postal Code field cannot be null")
    private String postalCode;

    @NotNull(message = "State field cannot be null")
    @Schema(example = "PB")
    private String state;

    @NotNull(message = "City field cannot be null")
    @Schema(example = "Campina Grande")
    private String city;

    @NotNull(message = "District field cannot be null")
    @Schema(example = "Centro")
    private String district;

    @NotNull(message = "Street field cannot be null")
    @Schema(example = "Rua Exemplo")
    private String street;

    @Schema(example = "123")
    @NotNull(message = "Number field cannot be null")
    private String number;

    @Schema(example = "Complemento")
    private String complement;
}
