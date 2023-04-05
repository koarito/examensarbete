package se.koarito.examensarbete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.koarito.examensarbete.data.domain.Team;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.dto.TeamDto;

import java.util.Set;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Set<TeamDto> getTeamsByDevelopersContaining(User user);
}
