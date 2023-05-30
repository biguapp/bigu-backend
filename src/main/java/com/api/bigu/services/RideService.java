package com.api.bigu.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.api.bigu.dto.candidate.CandidateRequest;
import com.api.bigu.dto.candidate.CandidateResponse;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
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
            throw new NoCarsFoundException();
        } else return user;
    }

    public User getRider(Integer userId, Integer addressId) throws UserNotFoundException, AddressNotFoundException {
        User user;
        user = userService.findUserById(userId);
        if (user.getAddresses().containsKey(addressService.getAddressById(addressId).getNickname())){
            return user;
        } else throw new AddressNotFoundException("Endereço não encontrado.");
    }

    public RideResponse createRide(RideRequest rideRequest, User driver) throws CarNotFoundException {
        Ride ride = rideMapper.toRide(rideRequest);
        Integer carId = rideRequest.getCarId();
        List<User> members = new ArrayList<>();

        if (Objects.equals(driver.getSex(), "M")) ride.setToWomen(false);

        ride.setCar(carService.findCarById(carId).get());
        members.add(driver);
        ride.setMembers(members);

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
            if (Objects.equals(user.getUserId(), userId)) {
                return user;
            }
        } throw new UserNotFoundException("Usuário não está na carona.");
    }

    private Ride registerRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public RideResponse requestRide(Integer userId, CandidateRequest candidateRequest) throws UserNotFoundException, RideIsFullException {
        Candidate candidate = candidateMapper.toCandidate(userId, candidateRequest);
        Ride ride = rideRepository.findById(candidateRequest.getRideId()).get();
        if (ride.getNumSeats() > ride.getMembers().size() - 1) {
            ride.getCandidates().add(candidate);
        } else throw new RideIsFullException("A carona está cheia.");
        return rideMapper.toRideResponse(ride);
    }

    public RideResponse acceptCandidate(CandidateResponse candidateResponse) throws RideNotFoundException, UserNotFoundException {
        Ride ride = rideRepository.findById(candidateResponse.getRideId()).get();
        for (Candidate candidate: ride.getCandidates()) {
            if (candidate.getUserId().equals(candidateResponse.getUserId())){
                if (candidateResponse.isAccepted()){
                    ride.getMembers().add(userService.findUserById(candidate.getUserId()));
                }
                ride.getCandidates().remove(candidate);
            }
        }
        return rideMapper.toRideResponse(ride);
    }
}
