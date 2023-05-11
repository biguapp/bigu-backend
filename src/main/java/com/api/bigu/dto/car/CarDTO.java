package com.api.bigu.dto.car;

import com.api.bigu.models.Car;
import com.api.bigu.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarDTO {
    private Integer id;
    private User user;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private String plate;

    public CarDTO() {
    }

    public CarDTO(Integer id, User user, String brand, String model, Integer year, String color, String plate) {
        this.id = id;
        this.user = user;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
    }

    public CarDTO(Car car) {
        this.id = car.getId();
        this.user = car.getUser();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.color = car.getColor();
        this.plate = car.getPlate();
    }

    public User getUser() { return user;  }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return user.getUserId();
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getPlate() {
        return plate;
    }

    public Car toEntity() {
        return new Car(id, user, brand, model, year, color, plate);
    }

    public static List<CarDTO> toDTOList(List<Car> carList) {
        List<CarDTO> list = new ArrayList<>();
        for (Car car : carList) {
            list.add(new CarDTO(car));
        }
        return list;
    }
}
