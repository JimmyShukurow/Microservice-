package io.smartir.smartir.website.service;

import io.smartir.smartir.website.entity.Feedback;
import io.smartir.smartir.website.entity.Type;
import io.smartir.smartir.website.repository.FeedbackRepository;
import io.smartir.smartir.website.repository.TypeRepository;
import io.smartir.smartir.website.requests.FeedbackRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

  private final FeedbackRepository feedbackRepository;


    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public String addFeedback(FeedbackRequest request){
        Feedback feedback =new Feedback();
        feedback.setEmail(request.getEmail());
        feedback.setFeedback(request.getFeedback());
        feedbackRepository.save(feedback);
        return "Feedback sent";
    }
}
