package com.api.bigu.services;

import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.models.Ride;
import com.api.bigu.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper addressMapper;

    public Ride toRide(RideRequest rideRequest) {
        return Ride.builder()
                .startAddress(addressRepository.findById(rideRequest.getStartAddressId()).get())
                .destinationAddress(addressRepository.findById(rideRequest.getDestinationAddressId()).get())
                .numSeats(rideRequest.getNumSeats())
                .goingToCollege(rideRequest.isGoingToCollege())
                .price(rideRequest.getPrice())
                .scheduledTime(rideRequest.getDateTime())
                .description(rideRequest.getDescription())
                .toWomen(rideRequest.isToWomen())
                .build();
    }

    public RideResponse toRideResponse(Ride rideCreated) {
        return RideResponse.builder()
                .goingToCollege(rideCreated.isGoingToCollege())
                .members(rideCreated.getMembers())
                .candidates(rideCreated.getCandidates())
                .start(addressMapper.toAddressResponse(rideCreated.getStartAddress()))
                .destination(addressMapper.toAddressResponse(rideCreated.getDestinationAddress()))
                .dateTime(rideCreated.getScheduledTime())
                .numSeats(rideCreated.getNumSeats())
                .price(rideCreated.getPrice())
                .toWomen(rideCreated.isToWomen())
                .carId(rideCreated.getCar().getId())
                .description(rideCreated.getDescription())
                .build();
    }
}
