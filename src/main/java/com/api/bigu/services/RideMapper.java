/**
package com.api.bigu.services;

import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.user.UserResponse;
import com.api.bigu.models.Ride;
import com.api.bigu.user.User;
import com.api.bigu.repositories.AddressRepository;
import com.api.bigu.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RideMapper {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CarMapper carMapper;

    public Ride toRide(RideRequest rideRequest) {
        return Ride.builder()
                .startAddress(addressRepository.findById(rideRequest.getStartAddressId()).get())
                .destinationAddress(addressRepository.findById(rideRequest.getDestinationAddressId()).get())
                .numSeats(rideRequest.getNumSeats())
                .goingToCollege(rideRequest.getGoingToCollege())
                .price(rideRequest.getPrice())
                .scheduledTime(rideRequest.getDateTime())
                .description(rideRequest.getDescription())
                .toWomen(rideRequest.getToWomen())
                .build();
    }

    public RideResponse toRideResponse(Ride rideCreated) {
        return RideResponse.builder()
                .id(rideCreated.getRideId())
                .goingToCollege(rideCreated.getGoingToCollege())
                .driver(userMapper.toUserResponse(rideCreated.getMembers().get(0)))
                .riders(toRidersResponse(rideCreated.getMembers()))
                .start(addressMapper.toAddressResponse(rideCreated.getStartAddress()))
                .destination(addressMapper.toAddressResponse(rideCreated.getDestinationAddress()))
                .dateTime(rideCreated.getScheduledTime())
                .numSeats(rideCreated.getNumSeats() - rideCreated.getMembers().size() + 1)
                .price(rideCreated.getPrice())
                .toWomen(rideCreated.getToWomen())
                .car(carMapper.toCarResponse(rideCreated.getCar()))
                .description(rideCreated.getDescription())
                .build();
    }

    public List<UserResponse> toRidersResponse(List<User> riders) {
        List<UserResponse> ridersResponse = new ArrayList<>();
        for( User user : riders){
            ridersResponse.add(userMapper.toUserResponse(user));
        }
        return ridersResponse;
    }
}
*/
