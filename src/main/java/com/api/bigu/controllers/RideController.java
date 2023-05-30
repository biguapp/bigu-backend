package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.dto.ride.RideDTO;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.exceptions.*;
import com.api.bigu.models.Ride;
import com.api.bigu.models.User;
import com.api.bigu.services.*;
import com.api.bigu.util.errors.AddressError;
import com.api.bigu.util.errors.CarError;
import com.api.bigu.util.errors.RideError;
import com.api.bigu.util.errors.UserError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    @Autowired
    CandidateMapper candidateMapper;

    @Autowired
    RideService rideService;

    @Autowired
    CarService carService;

    @Autowired
    JwtService jwtService;

    @Autowired
    RideMapper rideMapper;

    @GetMapping
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<?> searchById(@PathVariable Integer rideId){
        try{
            RideResponse ride = rideMapper.toRideResponse(rideService.findRideById(rideId));
            return ResponseEntity.ok(ride);
        } catch (RideNotFoundException e) {
            return RideError.rideNotFoundError();
        }
    }

    @GetMapping("/{rideId}/members")
    public ResponseEntity<?> getRideMembers(@PathVariable Integer rideId){
        try{
            List<User> members = rideService.getRideMembers(rideId);
            return ResponseEntity.ok(members);
        } catch (RideNotFoundException e) {
            return RideError.rideNotFoundError();
        }
    }

    @GetMapping("/{rideId}/{memberId}")
    public ResponseEntity<?> getRideMember(@PathVariable Integer rideId, @PathVariable Integer memberId){
        try{
            User member = rideService.getRideMember(rideId, memberId);
            return ResponseEntity.ok(member);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (RideNotFoundException e) {
            return RideError.rideNotFoundError();
        }
    }


    @PostMapping()
    public ResponseEntity<?> createRide(@RequestHeader("Authorization") String authorizationHeader, @RequestBody RideRequest rideRequest) {
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User driver = rideService.getDriver(userId);

            if (!(jwtService.isTokenValid(jwtService.parse(authorizationHeader), driver))) {
                return UserError.userBlockedError();
            }

            return ResponseEntity.ok(rideService.createRide(rideRequest, driver));

        } catch (CarNotFoundException cNFE) {
            return CarError.carNotFoundError();
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (NoCarsFoundException nCFE) {
            return CarError.noCarsFoundError();
        }
    }

    @PutMapping("/request-ride")
    public ResponseEntity<?> requestRide(@RequestBody CandidateRequest candidateRequest){
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(candidateRequest.getAuthorizationHeader()));
            User rider = rideService.getRider(userId, candidateRequest.getAddressId());
            if (!(jwtService.isTokenValid(jwtService.parse(candidateRequest.getAuthorizationHeader()), rider))) {
                return UserError.userBlockedError();
            }

            return ResponseEntity.ok(rideService.requestRide(userId, candidateRequest));

        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (AddressNotFoundException e){
            return AddressError.addressNotFoundError();
        } catch (RideIsFullException e) {
            return RideError.rideIsFullError();
        }

    }

    @PutMapping("/answer-candidate")
    public ResponseEntity<?> answerCandidate(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CandidateResponse candidateResponse){
        try {
            Integer driverId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User driver = rideService.getDriver(driverId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), driver)) {
                return ResponseEntity.ok(rideService.acceptCandidate(candidateResponse));
            } else return UserError.userBlockedError();
        } catch (CarNotFoundException cNFE) {
            return CarError.carNotFoundError();
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (NoCarsFoundException nCFE) {
            return CarError.noCarsFoundError();
        } catch (RideNotFoundException e) {
            return RideError.rideNotFoundError();
        }
    }

    @DeleteMapping("delete-ride/{rideId}")
    public ResponseEntity<?> cancelRide(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer rideId) {
        try {
            Integer driverId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User driver = rideService.getDriver(driverId);

            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), driver)) {
                rideService.deleteRideById(rideId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return UserError.userBlockedError();

        } catch (CarNotFoundException cNFE) {
            return CarError.carNotFoundError();
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (NoCarsFoundException nCFE) {
            return CarError.noCarsFoundError();
        } catch (RideNotFoundException e) {
            return RideError.rideNotFoundError();
        }
    }


}

