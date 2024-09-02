package com.arturfrimu.jwt.auth;

import com.arturfrimu.jwt.user.Role;
import lombok.Builder;

@Builder
public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        Role role
) {
}
