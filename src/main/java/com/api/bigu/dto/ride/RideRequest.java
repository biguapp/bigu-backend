package com.api.bigu.dto.ride;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.models.Address;
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
    private Integer driverId;

    @NonNull
    private boolean goingToCollege;

    @NonNull
    private AddressRequest start;

    @NonNull
    private AddressRequest destination;

    @NonNull
    private LocalDateTime dateTime;

    @NonNull
    @Schema(example = "3")
    private int numSeats;

    @NonNull
    @Schema(example = "8.90")
    private float price;

    @NonNull
    private boolean toWomen;

    @NonNull
    private Integer carId;

    private String description;
}
