package com.api.bigu.models;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;

    @Column(name = "driver", nullable = false)
    private User driver;

    @ManyToOne
    @JoinColumn(name="start_address_id")
    private Address startAddress;

    @ManyToOne
    @JoinColumn(name="destination_address_id")
    private Address destinationAddress;

    @Column(name = "distance", nullable = false)
    private float distance;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
