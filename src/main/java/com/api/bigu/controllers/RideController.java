package com.api.bigu.controllers;

import com.api.bigu.dto.ride.RideDTO;
import com.api.bigu.exceptions.RideNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Ride;
import com.api.bigu.services.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    @Autowired
    RideService rideService;

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

    @GetMapping("/{memberId}")
    public ResponseEntity<?> searchByMember(@PathVariable Integer memberId){
        try{
            Optional<Ride> rides = rideService.findByMember(memberId);
            return ResponseEntity.ok(rides);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse passageiro não tem caronas registradas.", e);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", e);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse passageiro não existe.", e);
        }
    }

//TODO TERMINAR, DEU SONO
//    @PostMapping("/{rideId}")
//    public ResponseEntity<?> createRide(@RequestBody RideDTO rideDTO){
//        try{
//            //Ride ride = new Ride(rideDTO...)
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
//        }
//    }


}