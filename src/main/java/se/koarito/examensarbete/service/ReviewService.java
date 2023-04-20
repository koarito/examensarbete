package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.domain.Feedback;
import se.koarito.examensarbete.data.domain.Review;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.enm.Status;
import se.koarito.examensarbete.data.requestbody.CreateReviewRequest;
import se.koarito.examensarbete.repository.FeedbackRepository;
import se.koarito.examensarbete.repository.ReviewRepository;
import se.koarito.examensarbete.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FeedbackRepository feedbackRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void createReview(CreateReviewRequest request, String token) {
        Set<User> reviewers = new HashSet<>(userRepository.findAllById(request.getReviewersIds()));
        long authorId = (int) jwtService.extractClaim(token.substring(7), claims -> claims.get("UserId"));
        Review review = Review.builder()
                .jiraId(request.getJiraId())
                .gitLink(request.getGitLink())
                .branch(request.getBranch())
                .author(userRepository.getReferenceById(authorId))
                .reviewers(reviewers)
                .status(Status.INCOMPLETE)
                .build();

        Set<Feedback> grades = reviewers.stream().map(reviewer -> Feedback.builder().review(review)
                .user(reviewer).build()).collect(Collectors.toSet());
        review.setGrades(grades);
        reviewRepository.save(review);

    }


}
