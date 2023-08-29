package com.api.bigu.services;

import com.api.bigu.dto.feedback.FeedbackMapper;
import com.api.bigu.dto.feedback.FeedbackRequest;
import com.api.bigu.dto.feedback.FeedbackResponse;
import com.api.bigu.exceptions.FeedbackNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Feedback;
import com.api.bigu.repositories.FeedbackRepository;
import com.api.bigu.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private UserService userService;

    public FeedbackResponse getFeedback(Integer feedbackId){
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        return feedbackMapper.toFeedbackResponse(feedback);
    }

    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) throws UserNotFoundException {
        Feedback feedback;
        if (feedbackRequest.getComment().isEmpty()){
            feedback = Feedback.builder()
                    .rideId(feedbackRequest.getRideId()).senderId(feedbackRequest.getSenderId()).receiverId(feedbackRequest.getReceiverId())
                    .score(feedbackRequest.getScore()).build();
        } else {
            feedback = Feedback.builder()
                    .rideId(feedbackRequest.getRideId()).senderId(feedbackRequest.getSenderId()).receiverId(feedbackRequest.getReceiverId())
                    .score(feedbackRequest.getScore()).comment(feedbackRequest.getComment()).build();
        }
        feedbackRepository.save(feedback);
        userService.addFeedbackToUser(feedback.getReceiverId(), feedback);
        userService.addFeedbackToUser(feedback.getSenderId(), feedback);
        return feedbackMapper.toFeedbackResponse(feedback);

    }

    public void deleteFeedback(Integer feedbackId, Integer senderId) throws FeedbackNotFoundException {
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        if(feedback.getSenderId().equals(senderId)){
            feedbackRepository.delete(feedback);
            userService.deleteFeedbackFromUser(senderId, feedback);
            userService.deleteFeedbackFromUser(feedback.getReceiverId(), feedback);
        }

    }

}
