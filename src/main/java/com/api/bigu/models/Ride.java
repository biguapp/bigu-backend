package com.api.bigu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer id;

    @Column(name = "driver_id")
    private Integer driverId;

    @ManyToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<User> members;

    @Column(name = "num_seats", nullable = false)
    private int numSeats;

    @Column(name = "to_college")
    private boolean goingToCollege; // indo para uf (true) ou saindo dela (false)?
    //TODO setar endereços de acordo com boolean acima

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

    //TODO correção do relacionamento com endereço
    //TODO setar distance de acordo com endereços (maps api?)
//    @Column(name = "start_address", nullable = false)
//    private Address startAddress;
//
//    @Column(name = "destination_address", nullable = false)
//    private Address destinationAddress;

    //TODO criar construtor adequado
	public Integer getRideId() {
		return this.id;
	}


}
