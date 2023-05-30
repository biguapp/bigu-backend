package com.api.bigu.services;

import com.api.bigu.dto.car.CarDTO;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.NoCarsFoundException;
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
    private CarMapper carMapper;

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

    public void deleteById(Integer carId) throws CarNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new CarNotFoundException();
        }
        carRepository.deleteById(carId);
    }

    public CarResponse addCarToUser(Integer userId, CarRequest carRequest) throws UserNotFoundException {
        Car car = carMapper.toCar(userId, carRequest);
        userService.findUserById(userId).getCars().put(car.getPlate(), car);
        carRepository.save(car);
        return carMapper.toCarResponse(car);
    }

    //@SneakyThrows
    public List<Car> findCarsByUserId(Integer userId) throws UserNotFoundException, NoCarsFoundException {
        List<Car> cars = userService.findUserById(userId).getCars().values().stream().toList();
        if (cars.isEmpty()) throw new NoCarsFoundException();
        return cars;
    }

    public void removeCarFromUser(Integer userId, Integer carId) throws UserNotFoundException, CarNotFoundException {
        User user = userService.findUserById(userId);
        Car car = findCarById(carId).orElseThrow(CarNotFoundException::new);
        if (user.getCars().containsKey(car.getPlate())) {
            user.getCars().remove(car.getPlate());
            carRepository.delete(car);
        }
    }
}
