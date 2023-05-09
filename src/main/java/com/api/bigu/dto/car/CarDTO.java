package com.api.bigu.dto.car;

import com.api.bigu.models.Car;

public class CarDTO {
    private Integer id;
    private Integer userId;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private String plate;

    public CarDTO() {
    }

    public CarDTO(Integer id, Integer userId, String brand, String model, Integer year, String color, String plate) {
        this.id = id;
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
    }

    public CarDTO(Car car) {
        this.id = car.getId();
        this.userId = car.getUserId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.color = car.getColor();
        this.plate = car.getPlate();
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
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
        return new Car(id, userId, brand, model, year, color, plate);
    }
}
