package com.api.bigu.services;

import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.models.Address;
import com.api.bigu.models.Ride;
import com.api.bigu.repositories.RideRepository;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    AddressMapper addressMapper;

    public Ride toRide(RideRequest rideRequest) {
        return Ride.builder()
                .driverId(rideRequest.getDriverId())
                .startAddress(addressMapper.toAddress(rideRequest.getStart()))
                .destinationAddress(addressMapper.toAddress(rideRequest.getDestination()))
                .numSeats(rideRequest.getNumSeats())
                .goingToCollege(rideRequest.isGoingToCollege())
                .price(rideRequest.getPrice())
                .scheduledTime(rideRequest.getDateTime())
                .description(rideRequest.getDescription())
                .toWomen(rideRequest.isToWemen())
                .build();
    }

    public RideResponse toRideResponse(Ride rideCreated) {
        return RideResponse.builder()
                .goingToCollege(rideCreated.isGoingToCollege())
                .members(rideCreated.getMembers())
                .start(addressMapper.toAddressResponse(rideCreated.getStartAddress()))
                .destination(addressMapper.toAddressResponse(rideCreated.getDestinationAddress()))
                .dateTime(rideCreated.getScheduledTime())
                .numSeats(rideCreated.getNumSeats())
                .price(rideCreated.getPrice())
                .toWemen(rideCreated.isToWomen())
                .carId(rideCreated.getCar().getId())
                .description(rideCreated.getDescription())
                .build();
    }
}
