package com.arturfrimu.common.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Phone number can not be blank")
        String phoneNumber,
        @NotBlank(message = "Username can not be blank")
        String username,
        @NotBlank(message = "Password can not be blank")
        String password
) {
}