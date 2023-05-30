package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.car.CarDTO;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.NoCarsFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.services.CarService;
import com.api.bigu.util.errors.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){ return ResponseEntity.ok(carRepository.findAll());}

    @GetMapping
    public ResponseEntity<?> getUserCars(@RequestHeader("Authorization") String authorizationHeader) throws UserNotFoundException, NoCarsFoundException {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        List<CarDTO> dtoList = CarDTO.toDTOList(carService.findCarsByUserId(userId));
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CarRequest carRequest) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            return ResponseEntity.ok(carService.addCarToUser(userId, carRequest));
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> removeCar(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer carId) throws UserNotFoundException, CarNotFoundException {
        carService.removeCarFromUser(jwtService.extractUserId(jwtService.parse(authorizationHeader)), carId);
        return ResponseEntity.noContent().build();
    }
}
