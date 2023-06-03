package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.NoCarsFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.services.CarService;
import com.api.bigu.util.errors.AuthError;
import com.api.bigu.util.errors.CarError;
import com.api.bigu.util.errors.UserError;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<CarResponse> carList = new ArrayList<>();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            carList = carService.findCarsByUserId(userId);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (NoCarsFoundException e) {
            return CarError.noCarsFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }

        return ResponseEntity.ok(carList);
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CarRequest carRequest) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            return ResponseEntity.ok(carService.addCarToUser(userId, carRequest));
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeCar(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Integer carId) {
        try {
            carService.removeCarFromUser(jwtService.extractUserId(jwtService.parse(authorizationHeader)), carId);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (CarNotFoundException e) {
            return CarError.carNotFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }

        return ResponseEntity.ok("Carro removido.");
    }
}
