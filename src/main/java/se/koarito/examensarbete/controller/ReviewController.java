package se.koarito.examensarbete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public long createReview(@RequestBody CreateReviewRequest request) {
        return reviewService.createReview(request);
    }

    @PatchMapping("/update/grade")
    public long updateGrade(@RequestParam long feedbackId, @RequestParam String grade) {
        return reviewService.updateGrade(feedbackId, grade);
    }

    @GetMapping("/get/review/{reviewId}")
    public ReviewDto getReview(@PathVariable long reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PutMapping("/edit/{reviewId}")
    public long editReview(@PathVariable long reviewId, @RequestBody CreateReviewRequest request) {
        return reviewService.updateReview(reviewId, request);
    }

    @GetMapping("/get/reviews")
    public Set<ReviewDto> getOwnReviews() {
        return reviewService.getUserReviews();
    }

    @GetMapping("/get/assigned/reviews")
    public Set<ReviewDto> getAssignedReviews() {
        return reviewService.getAssignedReviews();
    }

}
