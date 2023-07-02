/**
package com.api.bigu.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "TB_CANDIDATE")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer candidateId;

    @Column(name= "phone_number")
    private String phoneNumber;

    @Column(name= "user_id")
    private Integer userId;

    @Column(name = "ride_id")
    private Integer rideId;

    @Column(name= "address_id")
    private Integer addressId;
}
*/
