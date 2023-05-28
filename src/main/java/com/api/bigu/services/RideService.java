package com.api.bigu.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.exceptions.RideNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.bigu.models.Ride;
import com.api.bigu.models.User;
import com.api.bigu.repositories.RideRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import static com.api.bigu.models.enums.UserType.DRIVER;

@Service
@Transactional
@RequiredArgsConstructor
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private RideMapper rideMapper;

    public User getDriver(Integer userId) throws UserNotFoundException {
        User user;
        user = userService.findUserById(userId);
        if (carService.findCarsByUserId(userId).isEmpty()) {
            return null;
        } else return user;
    }

    public RideResponse createRide(RideRequest rideRequest, User driver) {
        Ride ride = rideMapper.toRide(rideRequest);
        Integer carId = rideRequest.getCarId();
        List<User> members = new ArrayList<>();

        if (Objects.equals(driver.getSex(), "M")) ride.setToWomen(false);

        ride.setCar(carService.findCarById(carId).get());
        members.add(driver);
        ride.setMembers(members);

        return rideMapper.toRideResponse(registerRide(ride));
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
// TODO CONSERTAR
//	public Optional<Ride> findByMember(Integer memberId) throws UserNotFoundException {
//		 List<User> membro = null;
//		 membro.add(userService.findUserById(memberId).get());
//		 return rideRepository.findByMembers(membro);
//
//	}

    public List<User> getRideMembers(Integer rideId) throws RideNotFoundException {
        Optional<Ride> ride = rideRepository.findById(rideId);
        List<User> members = null;
        if (ride.isPresent()) {
            members = ride.get().getMembers();
        }
        return members;
    }

    public User getRideMember(Integer rideId, Integer userId) throws UserNotFoundException, RideNotFoundException {
        List<User> members = this.getRideMembers(rideId);
        for (User user : members) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    private Ride registerRide(Ride ride) {
        return rideRepository.save(ride);
    }
}
