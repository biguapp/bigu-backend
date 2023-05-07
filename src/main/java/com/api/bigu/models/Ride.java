package com.api.bigu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;

    @Column(name = "driver", nullable = false)
    private User driver;

    // TODO correção do relacionamento com endereço
//    @Column(name = "start_address", nullable = false)
//    private Address startAddress;
//
//    @Column(name = "destination_address", nullable = false)
//    private Address destinationAddress;

    @Column(name = "distance", nullable = false)
    private float distance;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne()
    @JsonIgnore
    private User user;
}
