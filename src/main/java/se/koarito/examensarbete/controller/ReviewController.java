package se.koarito.examensarbete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.koarito.examensarbete.data.dto.ReviewDto;
import se.koarito.examensarbete.data.requestbody.CreateReviewRequest;
import se.koarito.examensarbete.service.ReviewService;

import java.util.Set;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public long createReview(@RequestBody CreateReviewRequest request, @RequestHeader(name = "Authorization") String token) {
        return reviewService.createReview(request, token);
    }

    @PatchMapping("/update/grade")
    public long updateGrade(@RequestParam long feedbackId, @RequestParam String grade) {
        return reviewService.updateGrade(feedbackId, grade);
    }

    @GetMapping("/get/review")
    public ReviewDto getReview(@RequestParam long reviewId) {
        return reviewService.getReview(reviewId);
    }

    @GetMapping("/get/reviews")
    public Set<ReviewDto> getOwnReviews(@RequestHeader(name = "Authorization") String token) {
        return reviewService.getUserReviews(token);
    }

    @GetMapping("/get/assigned/reviews")
    public Set<ReviewDto> getAssignedReviews(@RequestHeader(name = "Authorization") String token) {
        return reviewService.getAssignedReviews(token);
    }

}
