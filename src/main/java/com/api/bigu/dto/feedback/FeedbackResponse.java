package com.api.bigu.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

    private Integer id;

    private Integer rideId;

    private float score;

    private Integer senderId;

    private Integer receiverId;

    private String comment;
}
