package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.domain.Review;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.enm.Status;
import se.koarito.examensarbete.data.requestbody.CreateReviewRequest;
import se.koarito.examensarbete.repository.ReviewRepository;
import se.koarito.examensarbete.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public long createReview(CreateReviewRequest request){
        Set<User> reviewers = new HashSet<>(userRepository.findAllById(request.getReviewersIds()));
        //TODO- complete this
        Review review = Review.builder()
                .jiraId(request.getJiraId())
                .gitLink(request.getGitLink())
                .branch(request.getBranch())
                .author(userRepository.getReferenceById(request.getAuthorId()))
                .reviewers(reviewers)
                .status(Status.INCOMPLETE)
                .build();


    }
}
