package com.api.bigu.dto.ride;

import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.dto.user.UserResponse;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {

    private boolean goingToCollege;

    private UserResponse driver;

//    private List<Candidate> candidates;

    private AddressResponse start;

    private AddressResponse destination;

    private LocalDateTime dateTime;

    private int numSeats;

    private float price;

    private boolean toWomen;

    private CarResponse car;

    private String description;
}
