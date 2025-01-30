package org.example.practice.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.practice.entity.User;
import org.example.practice.repository.UserRepository;
import org.example.practice.security.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // If no token or invalid format, continue the filter chain
        }

        // Extract the token
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        try {
            // Validate and parse the token
            Jws<Claims> claimsJws = jwtService.extractJwt(token);
            Claims claims = claimsJws.getBody();
            String userId = claims.getSubject();

            // Fetch user from the database
            Optional<User> optionalUser = userRepository.findById(UUID.fromString(userId));
            if (optionalUser.isEmpty()) {
                filterChain.doFilter(request, response);
                return; // If user not found, continue the filter chain
            }

            User user = optionalUser.get();

            // Set the authentication in the security context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, // Principal
                    null, // Credentials (not needed for JWT)
                    user.getAuthorities() // User roles/authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // If token validation fails, continue the filter chain without authentication
            SecurityContextHolder.clearContext();
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}