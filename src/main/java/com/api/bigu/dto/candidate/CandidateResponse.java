package com.api.bigu.dto.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponse {

    private Integer candidateId;

    private boolean accepted;

    private String phoneNumber;

    private Integer rideId;

    private Integer userId;

    private Integer addressId;
}
