package com.api.bigu.dto.feedback;

import com.api.bigu.models.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest feedbackRequest){
        return Feedback.builder()
                .score(feedbackRequest.getScore())
                .rideId(feedbackRequest.getRideId())
                .senderId(feedbackRequest.getSenderId())
                .receiverId(feedbackRequest.getReceiverId())
                .comment(feedbackRequest.getComment())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback){
        return FeedbackResponse.builder()
                .id(feedback.getFeedbackId())
                .receiverId(feedback.getReceiverId())
                .senderId(feedback.getSenderId())
                .rideId(feedback.getRideId())
                .score(feedback.getScore())
                .comment(feedback.getComment()).
                build();
    }
}
