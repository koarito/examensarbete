package se.koarito.examensarbete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.koarito.examensarbete.data.domain.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
