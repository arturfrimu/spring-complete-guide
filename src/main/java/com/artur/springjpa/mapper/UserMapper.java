package com.artur.springjpa.mapper;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.service.command.CreateUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "personalInformation.username")
    @Mapping(source = "password", target = "personalInformation.password")
    User toUser(CreateUserCommand command);
}

