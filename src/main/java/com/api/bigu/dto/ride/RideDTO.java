package com.api.bigu.dto.ride;

import com.api.bigu.models.Car;
import com.api.bigu.models.Ride;
import com.api.bigu.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class RideDTO {

    public List<User> members;

    public int numSeats;

    public boolean goingToCollege;

    public float distance;

    public float price;

    public LocalDateTime scheduledTime;

    public Car car;

    public String description;

    public boolean toWomen;

    public RideDTO(Ride ride) {
        this.members = ride.getMembers();
        this.numSeats = ride.getNumSeats();
        this.goingToCollege = ride.isGoingToCollege();
        this.distance = ride.getDistance();
        this.price = ride.getPrice();
        this.scheduledTime = ride.getScheduledTime();
        this.car = ride.getCar();
        this.toWomen = ride.isToWomen();
        if (!ride.getDescription().isEmpty()){
            this.description = ride.getDescription();
        }

    }
}
