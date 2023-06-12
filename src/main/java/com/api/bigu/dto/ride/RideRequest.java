package com.api.bigu.dto.ride;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RideRequest {

    @NotNull(message =" Going to College field cannot be null")
    private Boolean goingToCollege;

    @NotNull(message = "Start Address Id field cannot be null")
    private Integer startAddressId;

    @NotNull(message = "Destination Address Id field cannot be null")
    private Integer destinationAddressId;

    @NotNull(message = "Date time field cannot be null")
    private LocalDateTime dateTime;

    @NotNull(message = "Num Seats field cannot be null")
    @Schema(example = "3")
    private Integer numSeats;

    @NotNull(message = "Price field cannot be null")
    @Schema(example = "8.90")
    private Double price;

    @NotNull(message = "To Women field cannot be null")
    private Boolean toWomen;

    @NotNull(message = "Card Id field cannot be null")
    private Integer carId;

    private String description;
}
