package com.arturfrimu.common.mapper;

import com.arturfrimu.common.controller.request.CreateUserRequest;
import com.arturfrimu.common.service.command.CreateUserCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CreateUserRequestCommandMapper {

    CreateUserCommand toCommand(CreateUserRequest request);
}
