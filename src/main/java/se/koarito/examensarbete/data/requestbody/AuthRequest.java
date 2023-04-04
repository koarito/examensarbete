package se.koarito.examensarbete.data.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
