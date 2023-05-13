package com.api.bigu.services;

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

    public void addCarToUser(Integer userId, Car car) {
        userService.addCarToUser(userId, car);
        carRepository.save(car);
    }

    public void removeCarFromUser(Integer userId, Integer carId) {
        userService.removeCarFromUser(userId, carId);
        carRepository.deleteById(carId);
    }

    @SneakyThrows
    public List<Car> findCarsByUserId(Integer userId) {
        return userService.findUserById(userId).get().getCars();
    }
}
