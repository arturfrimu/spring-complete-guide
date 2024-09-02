package com.arturfrimu.jwt.user;

import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {
}
