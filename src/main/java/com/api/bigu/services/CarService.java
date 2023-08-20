package com.api.bigu.services;

import com.api.bigu.dto.car.CarMapper;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.NoCarsFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import com.api.bigu.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            throw new CarNotFoundException("Carro não encontrado.");
        }

        return car;
    }

    public void deleteById(Integer carId) throws CarNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new CarNotFoundException("Carro não encontrado.");
        }
        carRepository.deleteById(carId);
    }

    public CarResponse addCarToUser(Integer userId, CarRequest carRequest) throws UserNotFoundException {
        Car car = carMapper.toCar(userId, carRequest);
        userService.findUserById(userId).getCars().put(car.getPlate(), car);
        carRepository.save(car);
        return carMapper.toCarResponse(car);
    }

    public List<CarResponse> findCarsByUserId(Integer userId) throws UserNotFoundException, NoCarsFoundException {
        List<Car> userCars = userService.findUserById(userId).getCars().values().stream().toList();
        List<CarResponse> carsResponse = new ArrayList<>();
        for (Car car: userCars
             ) {
            carsResponse.add(carMapper.toCarResponse(car));
        }
        return carsResponse;
    }

    public void removeCarFromUser(Integer userId, Integer carId) throws UserNotFoundException, CarNotFoundException {
        User user = userService.findUserById(userId);
        Car car = findCarById(carId).get();
        if (user.getCars().containsKey(car.getPlate())) {
            user.getCars().remove(car.getPlate());
            carRepository.delete(car);
        }
    }
}
