package com.api.bigu.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class CarResponse {

    private String brand;

    private String model;

    private String modelYear;

    private String color;

    private String plate;
}
