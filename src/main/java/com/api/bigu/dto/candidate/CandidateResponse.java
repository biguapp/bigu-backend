package com.api.bigu.dto.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CandidateResponse {

    private Integer candidateId;

    private boolean accepted;

    private Integer rideId;

    private Integer userId;

    private Integer addressId;
}
