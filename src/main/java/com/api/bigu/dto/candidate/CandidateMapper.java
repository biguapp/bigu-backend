package com.api.bigu.dto.candidate;

import com.api.bigu.dto.user.UserMapper;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.models.Candidate;
import com.api.bigu.dto.address.AddressMapper;
import com.api.bigu.services.AddressService;
import com.api.bigu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

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

    public CandidateResponse toCandidateResponse(Candidate candidateCreated) throws AddressNotFoundException {
        return CandidateResponse.builder()
                .candidateId(candidateCreated.getCandidateId())
                .userResponse(userMapper.toUserResponse(userService.findUserById(candidateCreated.getUserId())))
                .rideId(candidateCreated.getRideId())
                .addressResponse(addressService.getAddressById(candidateCreated.getAddressId()))
                .build();
    }
}
