package com.api.bigu.dto.candidate;

import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.user.UserResponse;
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

    private RideResponse rideResponse;

    private UserResponse userResponse;

    private AddressResponse addressResponse;
}
