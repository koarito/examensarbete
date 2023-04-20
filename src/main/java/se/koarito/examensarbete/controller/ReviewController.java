package se.koarito.examensarbete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.koarito.examensarbete.data.requestbody.CreateReviewRequest;
import se.koarito.examensarbete.service.ReviewService;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public void createReview(@RequestBody CreateReviewRequest request, @RequestHeader(name = "Authorization") String token) {
        reviewService.createReview(request, token);
    }
}
