package com.api.bigu.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.dto.user.UserResponse;
import com.api.bigu.exceptions.*;
import com.api.bigu.models.Candidate;
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
    private CandidateService candidateService;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private RideMapper rideMapper;

    public User getDriver(Integer userId) throws UserNotFoundException, NoCarsFoundException, CarNotFoundException {
        User user;
        user = userService.findUserById(userId);
        if (carService.findCarsByUserId(userId).isEmpty()) {
            throw new NoCarsFoundException("teste.");
        } else return user;
    }

    public User getUser(Integer userId) throws UserNotFoundException {
        User user;
        user = userService.findUserById(userId);
        return user;
    }


    public RideResponse createRide(RideRequest rideRequest, User driver) throws CarNotFoundException {
        Ride ride = rideMapper.toRide(rideRequest);
        Integer carId = rideRequest.getCarId();
        List<User> members = new ArrayList<>();
        if (Objects.equals(driver.getSex(), "M")) ride.setToWomen(false);
        ride.setCar(carService.findCarById(carId).get());
        members.add(driver);
        ride.setMembers(members);
        driver.getRides().add(ride);
        return rideMapper.toRideResponse(registerRide(ride));
    }

    public Ride findRideById(Integer rideId) throws RideNotFoundException {

        if (rideRepository.findById(rideId).isPresent()) {
            return rideRepository.findById(rideId).get();
        }
        else {
            throw new RideNotFoundException();
        }
    }

    public void deleteRideById(Integer rideId) throws RideNotFoundException {
        rideRepository.findById(rideId).orElseThrow(RideNotFoundException::new);
        //if (rideRepository.findById(rideId).get().getMembers().contains(this.getDriver(driverId))){
        rideRepository.deleteById(rideId);
        //}
    }

    public List<RideResponse> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        List<RideResponse> availableRides = new ArrayList<>();
        for (Ride ride: rides) {
                    availableRides.add(rideMapper.toRideResponse(ride));

        }
        return availableRides;
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

    public List<UserResponse> getRideMembers(Integer rideId) throws RideNotFoundException {
        Optional<Ride> ride = rideRepository.findById(rideId);
        List<UserResponse> members = null;
        if (ride.isPresent()) {
            for (User member: ride.get().getMembers()
                 ) {
                members.add(userService.toResponse(member));
            }
        }
        return members;
    }

    public UserResponse getRideMember(Integer rideId, Integer userId) throws UserNotFoundException, RideNotFoundException {
        List<UserResponse> members = this.getRideMembers(rideId);
        for (UserResponse user : members) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        } throw new UserNotFoundException("Usuário não está na carona.");
    }

    private Ride registerRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public CandidateResponse requestRide(Integer userId, CandidateRequest candidateRequest) throws UserNotFoundException, RideIsFullException, AddressNotFoundException {
        Candidate candidate = candidateService.createCandidate(userId, candidateRequest);
        CandidateResponse candidateResponse = candidateMapper.toCandidateResponse(candidate);
        Ride ride = rideRepository.findById(candidateRequest.getRideId()).get();
        if (ride.getNumSeats() > ride.getMembers().size() - 1) {
            ride.getCandidates().add(candidate);
        } else throw new RideIsFullException("A carona está cheia.");
        return candidateResponse;
    }

    public RideResponse acceptCandidate(CandidateResponse candidateResponse) throws RideNotFoundException, UserNotFoundException {
        Ride ride = rideRepository.findById(candidateResponse.getRideId()).get();
        List<Candidate> candidates = ride.getCandidates().stream().toList();
        System.err.println(candidates);
        System.err.println(ride.getCandidates());
        for (Candidate candidate: candidates) {
            if (candidate.getUserId().equals(candidateResponse.getUserId())){
                if (candidateResponse.isAccepted()){
                    ride.getMembers().add(userService.findUserById(candidate.getUserId()));
                }
                candidateService.removeCandidate(candidate.getCandidateId());
            }
        }
        return rideMapper.toRideResponse(ride);
    }

    public List<RideResponse> findAvailableRides(Integer userId) throws UserNotFoundException {
        List<Ride> rides = rideRepository.findAll();
        boolean isWomen = getUser(userId).getSex().equals("F");
        List<RideResponse> availableRides = new ArrayList<>();
        for (Ride ride: rides) {
            if (ride.getMembers().size() - 1 < ride.getNumSeats()){
                if ((isWomen && ride.isToWomen()) | !ride.isToWomen()){
                    availableRides.add(rideMapper.toRideResponse(ride));
                }
            }
        }
        return availableRides;
    }
}
