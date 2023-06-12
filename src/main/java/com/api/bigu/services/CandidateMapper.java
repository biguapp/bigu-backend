package com.api.bigu.services;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {
    public Candidate toCandidate(Integer userId, CandidateRequest candidateRequest) throws AddressNotFoundException {
        return Candidate.builder()
                .userId(userId)
                .rideId(candidateRequest.getRideId())
                .addressId(candidateRequest.getAddressId())
                .build();
    }

    public CandidateResponse toCandidateResponse(Candidate candidateCreated){
        return CandidateResponse.builder()
                .candidateId(candidateCreated.getCandidateId())
                .userId(candidateCreated.getUserId())
                .rideId(candidateCreated.getRideId())
                .addressId(candidateCreated.getAddressId())
                .build();
    }
}
