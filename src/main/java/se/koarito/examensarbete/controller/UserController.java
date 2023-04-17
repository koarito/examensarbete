package se.koarito.examensarbete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.koarito.examensarbete.data.dto.TeamDto;
import se.koarito.examensarbete.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/teams")
    public Set<TeamDto> getUserTeams(@RequestParam long userId){
        return userService.getUserTeams(userId);
    }
}
