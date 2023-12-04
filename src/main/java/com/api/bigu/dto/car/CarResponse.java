package com.api.bigu.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private Integer carId;

    private String brand;

    private String model;

    private String modelYear;

    private String color;

    private String plate;
}
