package com.artur.springjpa.controller.request;

import javax.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Phone number can not be blank")
        String phoneNumber,
        @NotBlank(message = "Username can not be blank")
        String username,
        @NotBlank(message = "Password can not be blank")
        String password
) {
}