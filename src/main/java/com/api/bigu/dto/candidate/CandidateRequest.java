package com.api.bigu.dto.candidate;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CandidateRequest {

    @NonNull
    private Integer rideId;

    @NonNull
    private Integer addressId;
}
