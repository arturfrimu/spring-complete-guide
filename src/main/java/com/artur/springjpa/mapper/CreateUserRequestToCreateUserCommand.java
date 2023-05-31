package com.artur.springjpa.mapper;

import com.artur.springjpa.controller.request.CreateUserRequest;
import com.artur.springjpa.service.command.CreateUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateUserRequestToCreateUserCommand {

    CreateUserRequestToCreateUserCommand INSTANCE = Mappers.getMapper( CreateUserRequestToCreateUserCommand.class );

    CreateUserCommand requestToCommand(CreateUserRequest request);
}
