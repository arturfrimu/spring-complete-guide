package com.artur.springjpa.controller;

import com.artur.springjpa.controller.request.CreateUserRequest;
import com.artur.springjpa.mapper.CreateUserRequestToCreateUserCommand;
import com.artur.springjpa.service.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    @GetMapping
    public void create(@Valid CreateUserRequest request) {
        var command = CreateUserRequestToCreateUserCommand.INSTANCE.requestToCommand(request);
        createUserUseCase.create(command);
    }
}
