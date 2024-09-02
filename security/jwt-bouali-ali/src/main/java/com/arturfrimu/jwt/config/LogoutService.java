package com.arturfrimu.jwt.config;

import com.arturfrimu.jwt.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LogoutService implements LogoutHandler {

    TokenRepository tokenRepository;

    @Override
    public void logout(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Authentication authentication
    ) {
        final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final var authorization = ofNullable(authHeader)
                .filter(a -> a.startsWith("Bearer "));
        if (authorization.isEmpty()) {
            return;
        }
        final var jwt = authHeader.substring(7);
        final var storedToken = tokenRepository.findByToken(jwt);
        if (storedToken.isPresent()) {
            storedToken.get().setExpired(true);
            storedToken.get().setRevoked(true);
            tokenRepository.save(storedToken.get());
            SecurityContextHolder.clearContext();
        }
    }
}
