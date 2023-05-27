package com.api.bigu.dto.ride;

import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.models.Ride;
import com.api.bigu.models.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.List;

public class RideDTO {
	
	public Integer id;
	
	public Integer driverId;

    public List<User> members;
    
    private Address startAddress;

    private Address destinationAddress;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public List<User> getMembers() {
		return members;
	}

	public Address getStartAddress() {
		return startAddress;
	}

	public Address getDestinationAddress() {
		return destinationAddress;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public boolean isGoingToCollege() {
		return goingToCollege;
	}

	public void setGoingToCollege(boolean goingToCollege) {
		this.goingToCollege = goingToCollege;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isToWomen() {
		return toWomen;
	}

	public void setToWomen(boolean toWomen) {
		this.toWomen = toWomen;
	}
}
