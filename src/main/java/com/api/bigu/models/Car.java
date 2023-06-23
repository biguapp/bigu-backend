package com.api.bigu.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "TB_CAR")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "modelYear", nullable = false)
    private String modelYear;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "plate", nullable = false)
    private String plate;
}
