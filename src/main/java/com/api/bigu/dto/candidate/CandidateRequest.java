package com.api.bigu.dto.candidate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CandidateRequest {

    @NotNull(message = "Ride id field cannot be null")
    private Integer rideId;

    @NotNull(message = "Address id field cannot be null")
    private Integer addressId;
}
