package com.artur.springjpa.mapper;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.service.command.CreateUserCommand;
import com.artur.springjpa.service.random.RandomCreateUserCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final UserMapper mapper = UserMapper.INSTANCE;

    @Test
    void testToUser() {
        var command = RandomCreateUserCommand.builder().build().get();
        var actual = mapper.toUser(command);
        var expected = new User(command.phoneNumber(), new UserPersonalInformation(command.username(), command.password()));
        assertEquals(expected, actual);
    }
}