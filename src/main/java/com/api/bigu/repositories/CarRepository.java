package com.api.bigu.repositories;

import com.api.bigu.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<List<Car>> findByUserId(Integer userId);
}
