package com.api.bigu.dto.ride;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RideRequest {

    @NonNull
    private boolean goingToCollege;

    @NonNull
    private Integer startAddressId;

    @NonNull
    private Integer destinationAddressId;

    @NonNull
    private LocalDateTime dateTime;

    @NonNull
    @Schema(example = "3")
    private int numSeats;

    @NonNull
    @Schema(example = "8.90")
    private double price;

    @NonNull
    private boolean toWomen;

    @NonNull
    private Integer carId;

    private String description;
}
