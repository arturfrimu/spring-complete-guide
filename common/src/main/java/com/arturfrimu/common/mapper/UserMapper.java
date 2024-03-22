package com.arturfrimu.common.mapper;

import com.arturfrimu.common.entity.User;
import com.arturfrimu.common.service.command.CreateUserCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(source = "username", target = "personalInformation.username")
//    @Mapping(source = "password", target = "personalInformation.password")
    User toUser(CreateUserCommand command);
}

