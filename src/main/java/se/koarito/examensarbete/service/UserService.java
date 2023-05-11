package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.dto.TeamDto;
import se.koarito.examensarbete.repository.TeamRepository;
import se.koarito.examensarbete.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public Set<TeamDto> getUserTeams() {
        // Returns teams without the logged in user as a dev
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return teamRepository.getTeamsByDevelopersContaining(userRepository.getReferenceById(user.getId())).stream()
                .map(teamDto -> {
                    Set<TeamDto.SimpleUser> developersWithoutUser = teamDto.getDevelopers().stream()
                            .filter(simpleUser -> simpleUser.getId() != user.getId())
                            .collect(Collectors.toSet());
                    return new TeamDto() {
                        @Override
                        public long getId() {
                            return teamDto.getId();
                        }

                        @Override
                        public String getName() {
                            return teamDto.getName();
                        }

                        @Override
                        public SimpleUser getTeamLeader() {
                            return teamDto.getTeamLeader();
                        }

                        @Override
                        public Set<SimpleUser> getDevelopers() {
                            return developersWithoutUser;
                        }
                    };
                })
                .collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }

}
