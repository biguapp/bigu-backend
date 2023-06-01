package com.api.bigu.dto.car;

import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class CarDTO {
    private Integer id;
    private Integer userId;
    private String brand;
    private String model;
    private String modelYear;
    private String color;
    private String plate;

    public CarDTO() {
    }

    public CarDTO(Integer id, Integer userId, String brand, String model, String modelYear, String color, String plate) {
        this.id = id;
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.color = color;
        this.plate = plate;
    }

    public CarDTO(Car car) {
        this.id = car.getId();
        this.userId = car.getUserId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.modelYear = car.getModelYear();
        this.color = car.getColor();
        this.plate = car.getPlate();
    }

    public Car toEntity() {
        return new Car(id, null, brand, model, modelYear, color, plate);
    }

    public static List<CarDTO> toDTOList(List<Car> carList) {
        List<CarDTO> list = new ArrayList<>();
        for (Car car : carList) {
            list.add(new CarDTO(car));
        }
        return list;
    }
}
