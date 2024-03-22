package com.arturfrimu.common.controller;

import com.arturfrimu.common.controller.request.CreateUserRequest;
import com.arturfrimu.common.mapper.CreateUserRequestCommandMapper;
import com.arturfrimu.common.service.transactional.propagation.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final CreateUserRequestCommandMapper createUserRequestCommandMapper;

    @PostMapping
    public ResponseEntity<?> create(@Valid CreateUserRequest request) {
        var command = createUserRequestCommandMapper.toCommand(request);
        createUserUseCase.create(command);
        return ResponseEntity.created(URI.create("/users")).build();
    }
}
