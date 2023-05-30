package com.api.bigu.dto.ride;

import com.api.bigu.dto.address.AddressRequest;
import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.models.Candidate;
import com.api.bigu.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RideResponse {

    private boolean goingToCollege;

    private List<User> members;

    private List<Candidate> candidates;

    private AddressResponse start;

    private AddressResponse destination;

    private LocalDateTime dateTime;

    private int numSeats;

    private float price;

    private boolean toWomen;

    private Integer carId;

    private String description;
}
