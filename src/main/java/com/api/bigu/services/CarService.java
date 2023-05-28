package com.api.bigu.services;

import com.api.bigu.dto.car.CarDTO;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
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

    public Optional<Car> findCarById(Integer carId) throws CarNotFoundException {
        Optional<Car> car;

        if (carRepository.findById(carId).isPresent()) {
            car = carRepository.findById(carId);
        }
        else {
            throw new CarNotFoundException();
        }

        return car;
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

    //@SneakyThrows
    public List<Car> findCarsByUserId(Integer userId) throws CarNotFoundException, UserNotFoundException {
        List<Car> cars = carRepository.findAllByUser(userService.findUserById(userId));

        if (cars.isEmpty()) throw new CarNotFoundException();

        return cars;
    }

    @SneakyThrows
    public void removeCarFromUser(Integer userId, Integer carId) {
        User user = userService.findUserById(userId);
        Car car = findCarById(carId).orElseThrow(() -> new Exception("Car not found"));
        if (car.getUser().equals(user)) {
            carRepository.delete(car);
        }
    }
}
