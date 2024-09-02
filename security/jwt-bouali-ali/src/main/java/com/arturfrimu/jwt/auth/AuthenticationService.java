package com.arturfrimu.jwt.auth;

import com.arturfrimu.jwt.service.JwtService;
import com.arturfrimu.jwt.token.Token;
import com.arturfrimu.jwt.token.TokenRepository;
import com.arturfrimu.jwt.token.TokenType;
import com.arturfrimu.jwt.user.User;
import com.arturfrimu.jwt.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

@Valid
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository repository;
    TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    AuthenticationManager authenticationManager;
    ObjectMapper objectMapper;

    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        final var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
        final var savedUser = repository.save(user);
        final var jwtToken = jwtService.generateToken(user);
        final var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final var user = repository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: %s".formatted(request.email())));
        final var jwtToken = jwtService.generateToken(user);
        final var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(@NotNull User user, @NotNull String jwtToken) {
        final var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(@NotNull User user) {
        final var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response
    ) throws IOException {
        final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final var authorization = ofNullable(authHeader)
                .filter(a -> a.startsWith("Bearer "));
        if (authorization.isEmpty()) {
            return;
        }

        final var refreshToken = authHeader.substring(7);
        final var userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail.isPresent()) {
            final var user = this.repository.findByEmail(userEmail.get())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with username: %s".formatted(userEmail.get())));

            if (jwtService.isTokenValid(refreshToken, user)) {
                final var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                final var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                objectMapper.writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
