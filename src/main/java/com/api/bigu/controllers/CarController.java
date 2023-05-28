package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.car.CarDTO;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.services.CarService;
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
    public ResponseEntity<?> getUserCars(@RequestHeader("Authorization") String authorizationHeader) throws UserNotFoundException, CarNotFoundException {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        List<CarDTO> dtoList = CarDTO.toDTOList(carService.findCarsByUserId(userId));
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CarDTO carDTO) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        carService.addCarToUser(carDTO);
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> removeCar(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer carId) {
        carService.removeCarFromUser(jwtService.extractUserId(jwtService.parse(authorizationHeader)), carId);
        return ResponseEntity.noContent().build();
    }
}
