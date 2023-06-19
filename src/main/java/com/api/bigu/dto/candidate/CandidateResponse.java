package com.api.bigu.dto.candidate;

import com.api.bigu.dto.user.UserResponse;
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

    private UserResponse userResponse;

    private Integer addressId;
}
