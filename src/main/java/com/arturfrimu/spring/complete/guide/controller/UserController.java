package com.arturfrimu.spring.complete.guide.controller;

import com.arturfrimu.spring.complete.guide.controller.request.CreateUserRequest;
import com.arturfrimu.spring.complete.guide.mapper.CreateUserRequestCommandMapper;
import com.arturfrimu.spring.complete.guide.service.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
