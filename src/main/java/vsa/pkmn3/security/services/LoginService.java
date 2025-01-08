package vsa.pkmn3.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import vsa.pkmn3.models.User;
import vsa.pkmn3.security.jwt.JwtService;

import javax.security.auth.login.CredentialException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public String login(String username, String rawPassword) throws UserPrincipalNotFoundException, CredentialException {
        if (!jdbcUserDetailsManager.userExists(username)) {
            throw new UserPrincipalNotFoundException("User %s not found".formatted(username));
        }

        UserDetails user = jdbcUserDetailsManager.loadUserByUsername(username);

        String encodedPasswordFromDb = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPasswordFromDb)) {
            throw new CredentialException("password is not valid");
        }

        return jwtService.createToken(user.getUsername(), user.getAuthorities().iterator().next());
    }

    public void signup(String username, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        jdbcUserDetailsManager.createUser(new User(username, encodedPassword, authorities, true));
    }
}
