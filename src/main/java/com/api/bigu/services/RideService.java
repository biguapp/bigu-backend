package com.api.bigu.services;

import java.util.List;
import java.util.Optional;

import com.api.bigu.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.bigu.models.Ride;
import com.api.bigu.models.User;
import com.api.bigu.repositories.RideRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RideService {
	
	 @Autowired
	 private RideRepository rideRepository;
	 
	 @Autowired
	 private UserService userService;
	 
	 public Integer registerRide(Ride ride) {
        if (ride != null) {
            rideRepository.save(ride);
        }
        return ride.getRideId();
	 }
	 
	public Optional<Ride> findRideById(Integer rideId) {
		return rideRepository.findById(rideId);
	}

    public void deleteRideById(Integer rideId) {
        rideRepository.deleteById(rideId);
    }
    
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }
    
    public void updateRide(Ride ride) {
	    if (rideRepository.findById(ride.getRideId()).isPresent()) {
	        rideRepository.save(ride);
	    }

    }

//	public List<Ride> getRideByUser(Integer userId) throws UserNotFoundException {
//
//	}

	public void deleteByUserId(Integer userId) { //deleta as caronas em que o user foi motorista ou passageiro
		//iduser -> caronas participadas -> para cada: 
								// idcarona -> deletar carona pelo idcarona
		
	}

	public Optional<Ride> findByMember(Integer memberId) throws UserNotFoundException {
		 List<User> membro = null;
		 membro.add(userService.findUserById(memberId).get());
		 return rideRepository.findByMembers(membro);

	}
}
