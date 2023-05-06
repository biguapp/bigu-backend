package com.api.bigu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "address_start", nullable = false)
    private Address addressStart;

    @Column(name = "address_destination", nullable = false)
    private Address addressDestination;

    @Column(name = "distance", nullable = false)
    private float distance;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne()
    @JsonIgnore
    private User user;
}
