package com.api.bigu.dto.candidate;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CandidateRequest {

    @NonNull
    private String authorizationHeader;

    @NonNull
    private Integer rideId;

    @NonNull
    private Integer addressId;
}
