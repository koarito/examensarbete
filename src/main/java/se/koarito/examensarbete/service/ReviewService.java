package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.domain.Feedback;
import se.koarito.examensarbete.data.domain.Review;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.dto.ReviewDto;
import se.koarito.examensarbete.data.enm.Grade;
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
    private final UserRepository userRepository;

    public long createReview(CreateReviewRequest request) {
        Set<User> reviewers = new HashSet<>(userRepository.findAllById(request.getReviewersIds()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review review = Review.builder()
                .jiraId(request.getJiraId())
                .gitLink(request.getGitLink())
                .branch(request.getBranch())
                .author(userRepository.getReferenceById(user.getId()))
                .reviewers(reviewers)
                .status(Status.INCOMPLETE)
                .build();

        Set<Feedback> grades = reviewers.stream()
                .map(reviewer -> Feedback.builder().review(review)
                        .user(reviewer)
                        .grade(Grade.VOID )
                        .build())
                .collect(Collectors.toSet());
        review.setGrades(grades);
        return reviewRepository.save(review).getId();
    }

    public long updateGrade(long reviewId, String grade) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.getGrades().stream()
                .filter(feedback -> feedback.getUser().getId() == user.getId())
                .findFirst()
                .ifPresent(feedback -> {
                    feedback.setGrade(Grade.valueOf(grade));
                    feedbackRepository.save(feedback);
                });

        if (review.getGrades().stream().filter(feedback -> feedback.getGrade() == Grade.POSITIVE).count() > review.getGrades().size() / 2) {
            review.setStatus(Status.COMPLETE);
            reviewRepository.save(review);
        } else {
            review.setStatus(Status.INCOMPLETE);
            reviewRepository.save(review);
        }

        return review.getId();
    }

    public ReviewDto getReview(long reviewId) {
        return reviewRepository.getReviewById(reviewId);
    }

    public Set<ReviewDto> getUserReviews() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return reviewRepository.getReviewsByAuthor(userRepository.getReferenceById(user.getId()));
    }

    public Set<ReviewDto> getAssignedReviews() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return reviewRepository.getReviewsByReviewersContaining(userRepository.getReferenceById(user.getId()));
    }

    public long updateReview(long reviewId, CreateReviewRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setGitLink(request.getGitLink());
        review.setJiraId(request.getJiraId());
        review.setBranch(request.getBranch());

        Set<Long> reviewersIds = review.getReviewers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        if (!reviewersIds.equals(request.getReviewersIds())) {
            Set<Feedback> gradesToBeDeleted = new HashSet<>(review.getGrades());

            Set<Feedback> grades = request.getReviewersIds().stream()
                    .map(userId -> Feedback.builder()
                            .review(review)
                            .user(userRepository.getReferenceById(userId))
                            .grade(Grade.VOID)
                            .build())
                    .collect(Collectors.toSet());
            review.setGrades(grades);
            review.setReviewers(new HashSet<>(userRepository.findAllById(request.getReviewersIds())));
            reviewRepository.save(review);
            feedbackRepository.deleteAll(gradesToBeDeleted);
        }

        return reviewRepository.save(review).getId();
    }

}
