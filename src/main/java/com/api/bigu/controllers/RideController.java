package com.api.bigu.controllers;

import com.api.bigu.dto.ride.RideDTO;
import com.api.bigu.exceptions.RideNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Ride;
import com.api.bigu.models.User;
import com.api.bigu.services.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
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
