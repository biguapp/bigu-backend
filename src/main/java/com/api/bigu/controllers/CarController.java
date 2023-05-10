package com.api.bigu.controllers;

import com.api.bigu.dto.car.CarDTO;
import com.api.bigu.models.User;
import com.api.bigu.services.CarService;
import com.api.bigu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CarDTO>> findCarsByUserId(@PathVariable Integer userId) {
        List<CarDTO> dtoList = CarDTO.toDTOList(carService.findCarsByUserId(userId));
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addCarToUser(@PathVariable Integer userId, @RequestBody CarDTO carDTO) {
        carService.addCarToUser(userId, carDTO.toEntity());
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{userId}/{carId}")
    public ResponseEntity<Void> removeCarFromUser(@PathVariable Integer userId, @PathVariable Integer carId) {
        carService.removeCarFromUser(userId, carId);
        return ResponseEntity.noContent().build();
    }
}
