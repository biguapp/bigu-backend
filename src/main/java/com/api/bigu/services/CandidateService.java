package com.api.bigu.services;

import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.exceptions.AddressNotFoundException;
import com.api.bigu.models.Candidate;
import com.api.bigu.repositories.CandidateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateMapper candidateMapper;

    public Candidate createCandidate(Integer userId, CandidateRequest candidateRequest) throws AddressNotFoundException {
        Candidate candidate = candidateMapper.toCandidate(userId, candidateRequest);
        candidateRepository.save(candidate);
        return candidate;
    }

    public void removeCandidate(Integer candidateId){
        candidateRepository.delete(candidateId);
    }
}
