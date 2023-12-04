package com.api.bigu.dto.feedback;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;

@Data
@Builder
@AllArgsConstructor
public class FeedbackRequest {

    @NotNull(message ="rideId can not be null.")
    private Integer rideId;

    @NotNull(message ="senderId can not be null.")
    private Integer senderId;

    @NotNull(message ="receiverId can not be null.")
    private Integer receiverId;

    @NotNull(message ="Score can not be null.")
    private float score;

    @Nullable
    private String comment;
}
