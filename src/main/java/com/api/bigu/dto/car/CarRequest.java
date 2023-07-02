/**
package com.api.bigu.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class CarRequest {

    @NotNull(message = "Brand field cannot be null")
    private String brand;

    @NotNull(message = "Model field cannot be null")
    private String model;

    @NotNull(message = "Model Year field cannot be null")
    @Schema(example = "2023")
    private String modelYear;

    @NotNull(message = "Color field cannot be null")
    private String color;

    @NotNull(message = "Plate field cannot be null")
    @Schema(example = "KGU7E07")
    private String plate;

}
*/
