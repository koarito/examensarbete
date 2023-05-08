package se.koarito.examensarbete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.koarito.examensarbete.data.domain.Review;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.dto.ReviewDto;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    ReviewDto getReviewById(long id);

    Set<ReviewDto> getReviewsByAuthor(User author);

    Set<ReviewDto> getReviewsByReviewersContaining(User user);

}
