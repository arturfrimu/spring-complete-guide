package com.arturfrimu.spring.complete.guide.mapper;

import com.arturfrimu.spring.complete.guide.controller.request.CreateUserRequest;
import com.arturfrimu.spring.complete.guide.service.command.CreateUserCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CreateUserRequestCommandMapper {

    CreateUserCommand toCommand(CreateUserRequest request);
}
