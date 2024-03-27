package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.requests.FeedbackRequest;
import io.smartir.smartir.website.service.FeedbackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

   private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("add-feedback")
    public String addFeedback(FeedbackRequest request) {
        return feedbackService.addFeedback(request);
    }

}


