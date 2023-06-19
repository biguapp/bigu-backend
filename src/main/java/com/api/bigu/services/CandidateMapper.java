package com.api.bigu.services;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public Candidate toCandidate(Integer userId, CandidateRequest candidateRequest) {
        return Candidate.builder()
                .userId(userId)
                .rideId(candidateRequest.getRideId())
                .phoneNumber(userService.findUserById(userId).getPhoneNumber())
                .addressId(candidateRequest.getAddressId())
                .build();
    }

    public CandidateResponse toCandidateResponse(Candidate candidateCreated){
        return CandidateResponse.builder()
                .candidateId(candidateCreated.getCandidateId())
                .userResponse(userMapper.toUserResponse(userService.findUserById(candidateCreated.getUserId())))
                .phoneNumber(candidateCreated.getPhoneNumber())
                .rideId(candidateCreated.getRideId())
                .addressId(candidateCreated.getAddressId())
                .build();
    }
}
