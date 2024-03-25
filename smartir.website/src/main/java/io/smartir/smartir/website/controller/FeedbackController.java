package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.entity.Type;
import io.smartir.smartir.website.requests.FeedbackRequest;
import io.smartir.smartir.website.service.FeedbackService;
import io.smartir.smartir.website.service.TypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


