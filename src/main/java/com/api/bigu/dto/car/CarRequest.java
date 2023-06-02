package com.api.bigu.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class CarRequest {

    @NonNull
    private String brand;

    @NonNull
    private String model;

    @NonNull
    @Schema(example = "2023")
    private String modelYear;

    @NonNull
    private String color;

    @NonNull
    @Schema(example = "KGU7E07")
    private String plate;

}
