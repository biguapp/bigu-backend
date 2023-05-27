package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.ride.RideDTO;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.exceptions.RideNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Ride;
import com.api.bigu.models.User;
import com.api.bigu.services.CarService;
import com.api.bigu.services.RideMapper;
import com.api.bigu.services.RideService;
import com.api.bigu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

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
            RideDTO ride = new RideDTO(rideService.findRideById(rideId).get());
            return ResponseEntity.ok(ride);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", e);
        }
    }

    @GetMapping("/{rideId}/members")
    public ResponseEntity<?> getRideMembers(@PathVariable Integer rideId){
        try{
            List<User> members = rideService.getRideMembers(rideId);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", e);
        } catch (RideNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carona não encontrada.", e);
        }
    }

    @GetMapping("/{rideId}/{memberId}")
    public ResponseEntity<?> getRideMember(@PathVariable Integer rideId, @PathVariable Integer memberId){
        try{
            User member = rideService.getRideMember(rideId, memberId);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", e);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.", e);
        } catch (RideNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carona não encontrada.", e);
        }
    }


    @PostMapping()
    public ResponseEntity<?> createRide(@RequestHeader("Authorization") String authorizationHeader, @RequestBody RideRequest rideRequest) throws UserNotFoundException {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        User driver = rideService.getDriver(userId);
        if (driver == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário não tem carros cadastrados");
        }
        Ride ride = null;
        if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), driver)) {
            try {
                ride = rideMapper.toRide(rideRequest);
                if (driver.getSex() == "M") ride.setToWomen(false);
                Integer carId = rideRequest.getCarId();
                ride.setCar(carService.findCarById(carId).get());
                List<User> members = new ArrayList<>();
                members.add(driver);
                ride.setMembers(members);

            } catch (Exception e) {
               throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
            }
        }
        return ResponseEntity.ok(rideMapper.toRideResponse(rideService.registerRide(ride)));
	}


}
