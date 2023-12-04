package com.api.bigu.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "TB_FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedbackId;

    private Integer rideId;

    private Integer senderId;

    private Integer receiverId;

    @Min(value = 0, message = "A pontuação deve ser no mínimo 0.")
    @Max(value = 5, message = "A pontuação deve ser no máximo 5.")
    private float score;

    private String comment;

}
