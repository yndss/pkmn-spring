package vsa.pkmn3.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vsa.pkmn3.models.LoginRequest;
import vsa.pkmn3.security.jwt.JwtService;
import vsa.pkmn3.security.services.LoginService;

import javax.security.auth.login.CredentialException;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthContoller {

    private final JwtService jwtService;

    private final LoginService loginService;

    @PostMapping("/success")
    public ResponseEntity<String> success(@AuthenticationPrincipal UserDetails user, HttpServletResponse response) {

        String jwt = jwtService.createToken(user.getUsername(), user.getAuthorities().iterator().next());
        response.addCookie(
                new Cookie("jwt", Base64.getEncoder().encodeToString(jwt.getBytes(StandardCharsets.UTF_8)))
        );

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok("Auth complete, JWT token is sent");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws UserPrincipalNotFoundException, CredentialException {
        String jwt = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody LoginRequest loginRequest) throws UserPrincipalNotFoundException, CredentialException {
        try {
            loginService.signup(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok("Signup complete");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user already exists");
        }
    }
}
