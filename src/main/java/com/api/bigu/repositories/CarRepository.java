package com.api.bigu.repositories;

import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
