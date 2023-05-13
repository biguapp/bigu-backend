package com.api.bigu.repositories;

import com.api.bigu.models.Ride;
import com.api.bigu.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Integer> {

    //Optional<Ride> findByMembers(List<User> member);
	
}
