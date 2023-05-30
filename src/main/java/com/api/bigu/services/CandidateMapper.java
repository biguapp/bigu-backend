package com.api.bigu.services;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.models.Address;
import com.api.bigu.models.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {
    public Candidate toCandidate(Integer userId, CandidateRequest candidateRequest) {
        return Candidate.builder()
                .userId(userId)
                .addressId(candidateRequest.getAddressId())
                .build();
    }
}
