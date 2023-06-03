package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.dto.user.UserResponse;
import com.api.bigu.exceptions.*;
import com.api.bigu.models.Ride;
import com.api.bigu.models.User;
import com.api.bigu.services.*;
import com.api.bigu.util.errors.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> getAllRides(@RequestHeader("Authorization") String authorizationHeader) {
        List<RideResponse> allRides = new ArrayList<>();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            jwtService.isTokenValid(jwtService.parse(authorizationHeader), rideService.getUser(userId));
            allRides = rideService.getAllRides();

        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }
        return ResponseEntity.ok(allRides);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<?> searchById(@PathVariable Integer rideId) {
        try {
            RideResponse ride = rideMapper.toRideResponse(rideService.findRideById(rideId));
            return ResponseEntity.ok(ride);
        } catch (RideNotFoundException rNFE) {
            return RideError.rideNotFoundError();
        }
    }

    @GetMapping("/{rideId}/members")
    public ResponseEntity<?> getRideMembers(@PathVariable Integer rideId) {
        try {
            List<UserResponse> members = rideService.getRideMembers(rideId);
            return ResponseEntity.ok(members);
        } catch (RideNotFoundException rNFE) {
            return RideError.rideNotFoundError();
        }
    }

    @GetMapping("/{rideId}/{memberId}")
    public ResponseEntity<?> getRideMember(@PathVariable Integer rideId, @PathVariable Integer memberId) {
        try {
            UserResponse member = rideService.getRideMember(rideId, memberId);
            return ResponseEntity.ok(member);
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (RideNotFoundException rNFE) {
            return RideError.rideNotFoundError();
        }
    }


    @PostMapping()
    public ResponseEntity<?> createRide(@RequestHeader("Authorization") String authorizationHeader, @RequestBody RideRequest rideRequest) {
        RideResponse rideResponse = new RideResponse();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User driver = rideService.getDriver(userId);
            rideResponse = rideService.createRide(rideRequest, driver);

        } catch (CarNotFoundException cNFE) {
            return CarError.carNotFoundError();
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (NoCarsFoundException nCFE) {
            return CarError.noCarsFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        } catch (InvalidTimeException e) {
            return RideError.invalidDateTimeError();
        }
        return ResponseEntity.ok(rideResponse);
    }

    @PutMapping("/request-ride")
    public ResponseEntity<?> requestRide(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CandidateRequest candidateRequest) {
        CandidateResponse candidateResponse = new CandidateResponse();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User rider = rideService.getUser(userId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), rider)) {
                candidateResponse = rideService.requestRide(userId, candidateRequest);
            }

        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (RideIsFullException rIFE) {
            return RideError.rideIsFullError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        } catch (AddressNotFoundException e) {
            return AddressError.addressNotFoundError();
        }
        return ResponseEntity.ok(candidateResponse);
    }

    @PutMapping("/answer-candidate")
    public ResponseEntity<?> answerCandidate(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CandidateResponse candidateResponse) {
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
        } catch (RideNotFoundException rNFE) {
            return RideError.rideNotFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
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
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableRides(@RequestHeader("Authorization") String authorizationHeader) {
        List<RideResponse> availableRides = new ArrayList<>();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), rideService.getUser(userId))) {
                availableRides = rideService.findAvailableRides(userId);
            }

        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }
        return ResponseEntity.ok(availableRides);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getMemberHistory(@RequestHeader("Authorization") String authorizationHeader) {
        List<RideResponse> memberRides = new ArrayList<>();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            jwtService.isTokenValid(jwtService.parse(authorizationHeader), rideService.getUser(userId));
            memberRides = rideService.getMemberHistory(userId);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }
        return ResponseEntity.ok(memberRides);
    }
}

