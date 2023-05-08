package se.koarito.examensarbete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.koarito.examensarbete.data.dto.AuthDto;
import se.koarito.examensarbete.data.requestbody.AuthRequest;
import se.koarito.examensarbete.data.requestbody.CreateUserRequest;
import se.koarito.examensarbete.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<Long> createUser(@RequestBody CreateUserRequest userRequest){
        return ResponseEntity.ok(authService.createUser(userRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthDto> authenticate(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}
