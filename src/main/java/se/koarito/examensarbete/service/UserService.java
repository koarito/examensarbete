package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.dto.TeamDto;
import se.koarito.examensarbete.repository.TeamRepository;
import se.koarito.examensarbete.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JwtService jwtService;

    public Set<TeamDto> getUserTeams(String token) {
        long userId = (int) jwtService.extractClaim(token.substring(7), claims -> claims.get("UserId"));
        return teamRepository.getTeamsByDevelopersContaining(userRepository.getReferenceById(userId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }

}
