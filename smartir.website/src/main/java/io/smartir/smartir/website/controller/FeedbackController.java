package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.requests.FeedbackRequest;
import io.smartir.smartir.website.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

   private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("add-feedback")
    public String addFeedback(@RequestBody FeedbackRequest request) {
        System.out.println(request);
        return feedbackService.addFeedback(request);
    }

    @GetMapping("status")
    public String getStatus() {
        return "Filter-service is working";
    }

}


