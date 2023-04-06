package se.koarito.examensarbete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.koarito.examensarbete.data.requestbody.CreateTeamRequest;
import se.koarito.examensarbete.service.TeamService;

@RestController
@RequestMapping("api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    ResponseEntity<String> createTeam(@RequestBody CreateTeamRequest createTeamRequest){
        return teamService.createTeam(createTeamRequest);
    }
}
