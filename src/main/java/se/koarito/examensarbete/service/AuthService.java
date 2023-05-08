package se.koarito.examensarbete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import se.koarito.examensarbete.data.domain.User;
import se.koarito.examensarbete.data.dto.AuthDto;
import se.koarito.examensarbete.data.enm.Role;
import se.koarito.examensarbete.data.requestbody.AuthRequest;
import se.koarito.examensarbete.data.requestbody.CreateUserRequest;
import se.koarito.examensarbete.repository.UserRepository;
import se.koarito.examensarbete.security.PasswordConfig;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public long createUser(CreateUserRequest requestBody) {
        User user = User.builder()
                .firstName(requestBody.getFirstName())
                .lastName(requestBody.getLastName())
                .email(requestBody.getEmail())
                .password(passwordConfig.bCryptEncoder().encode(requestBody.getPassword()))
                .role(Role.valueOf(requestBody.getRole()))
                .build();
        return userRepository.save(user).getId();
    }

    public AuthDto authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        var user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(Collections.emptyMap(), user);
        return new AuthDto(jwtToken);
    }
}
