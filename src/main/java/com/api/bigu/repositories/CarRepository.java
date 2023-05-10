package com.api.bigu.repositories;

import com.api.bigu.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByUserId(Integer userId);
}
