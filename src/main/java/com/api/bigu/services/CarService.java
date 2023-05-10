package com.api.bigu.services;

import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    public Car findCarById(Integer carId) {
        return carRepository.findById(carId)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteById(Integer carId) {
        carRepository.deleteById(carId);
    }

    public void addCarToUser(Integer userId, Car car) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        user.addCar(car);
        car.setUserId(userId);
        carRepository.save(car);
        userRepository.save(user);
    }

    public void removeCarFromUser(Integer userId, Integer carId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        user.removeCar(findCarById(carId));
        carRepository.deleteById(carId);
        userRepository.save(user);
    }

    public List<Car> findCarsByUserId(Integer userId) {
        return carRepository.findAllByUserId(userId);
    }
}
