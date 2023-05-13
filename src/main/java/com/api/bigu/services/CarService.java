package com.api.bigu.services;

import com.api.bigu.dto.car.CarDTO;
import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import com.api.bigu.repositories.CarRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;

    public Optional<Car> findCarById(Integer carId) {
        return carRepository.findById(carId);
    }

    public void deleteById(Integer carId) {
        carRepository.deleteById(carId);
    }

    @SneakyThrows
    public void addCarToUser(CarDTO carDTO) {
        Car car = carDTO.toEntity();
        car.setUser(userService.findUserById(carDTO.getUserId()));
        carRepository.save(car);
    }

    @SneakyThrows
    public List<Car> findCarsByUserId(Integer userId) {
        return carRepository.findAllByUser(userService.findUserById(userId));
    }
}
