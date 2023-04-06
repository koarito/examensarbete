package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.domain.Team;
import se.koarito.examensarbete.data.requestbody.CreateTeamRequest;
import se.koarito.examensarbete.repository.TeamRepository;
import se.koarito.examensarbete.repository.UserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

   public ResponseEntity<String> createTeam(CreateTeamRequest request) {
        try {
            Team team = Team.builder()
                    .name(request.getName())
                    .teamLeader(userRepository.getReferenceById(request.getTeamLeaderId()))
                    .developers(request.getDevsIds().stream().map(
                                    userRepository::getReferenceById
                            ).collect(Collectors.toSet())
                    ).build();
            return new ResponseEntity<>(String.valueOf(teamRepository.save(team).getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
