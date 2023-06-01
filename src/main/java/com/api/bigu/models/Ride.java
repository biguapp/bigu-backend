package com.api.bigu.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "TB_RIDE")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rideId;

    @Column(name = "driver_id")
    private Integer driverId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> members;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Candidate> candidates;

    @ManyToOne
    @JoinColumn(name="start_address_id")
    private Address startAddress;

    @ManyToOne
    @JoinColumn(name="destination_address_id")
    private Address destinationAddress;
    
    @Column(name = "num_seats", nullable = false)
    private int numSeats;

    @Column(name = "to_college")
    private boolean goingToCollege; // indo para uf (true) ou saindo dela (false)?

    @Column(name = "distance", nullable = false)
    private float distance;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "time", nullable = false)
    private LocalDateTime scheduledTime; // LocalDateTime dataHora =
                                         // LocalDateTime.of(AAAA, MM, DD, HH, MM, SS);

    @ManyToOne
    private Car car;

    @Column(name = "description")
    private String description;

    @Column(name = "women_only")
    private boolean toWomen;
}
