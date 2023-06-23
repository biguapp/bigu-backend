package com.api.bigu.services;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car toCar(Integer userId, CarRequest carRequest) {
        return Car.builder()
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .modelYear(carRequest.getModelYear())
                .color(carRequest.getColor())
                .plate(carRequest.getPlate())
                .userId(userId)
                .build();
    }

    public CarResponse toCarResponse(Car carCreated) {
        return CarResponse.builder()
                .carId(carCreated.getCarId())
                .brand(carCreated.getBrand())
                .model(carCreated.getModel())
                .modelYear(carCreated.getModelYear())
                .color(carCreated.getColor())
                .plate(carCreated.getPlate())
                .build();

    }
}
