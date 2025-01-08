package vsa.pkmn3.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import vsa.pkmn3.security.jwt.JwtService;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");

        if (Objects.isNull(jwtToken) || !jwtToken.startsWith("Bearer")) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("jwt")) {
                        jwtToken = new String(Base64.getDecoder().decode(cookie.getValue()));
                    }
                }
            }

            if (Objects.isNull(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }

        }

        if (jwtToken != null && jwtToken.startsWith("Bearer")) {
            jwtToken = jwtToken.split("Bearer ")[1];
        }

        UserDetails user = jwtService.verify(jwtToken);

        if (Objects.isNull(user)) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                user.getAuthorities()
        ));

        filterChain.doFilter(request, response);
    }
}
