package com.artur.springjpa.mapper;

import com.artur.springjpa.controller.request.CreateUserRequest;
import com.artur.springjpa.service.command.CreateUserCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CreateUserRequestCommandMapper {

    CreateUserCommand toCommand(CreateUserRequest request);
}
