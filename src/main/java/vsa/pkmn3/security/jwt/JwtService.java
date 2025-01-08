package vsa.pkmn3.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JdbcUserDetailsManager userDetailsService;

    @Value("${token.secret}")
    private String SECRET_KEY;

    @Value("${token.expiration}")
    private long TOKEN_EXPIRATION_MINUTES;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(SECRET_KEY);
    }

    public String createDummyToken() {
        return JWT.create()
                .withIssuer("pokemonApp")
                .withSubject("pokemonApp-admin")
                .withClaim("authority", "[ROLE_ADMIN]")
                .withExpiresAt(Instant.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    public String createToken(String username, GrantedAuthority authority) {
        return JWT.create()
                .withIssuer("pokemonApp")
                .withSubject(username)
                .withClaim("authority", authority.getAuthority())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_MINUTES*60*1000))
                .sign(algorithm);
    }

    public UserDetails verify(String jwt) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer("pokemonApp")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);

            UserDetails user = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
            if (Objects.isNull(user)) {
                return null;
            }

            return user;

        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
