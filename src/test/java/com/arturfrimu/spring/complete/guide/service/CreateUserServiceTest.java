package com.arturfrimu.spring.complete.guide.service;

import com.arturfrimu.spring.complete.guide.entity.User;
import com.arturfrimu.spring.complete.guide.entity.UserPersonalInformation;
import com.arturfrimu.spring.complete.guide.mapper.UserMapper;
import com.arturfrimu.spring.complete.guide.repository.UserRepository;
import com.arturfrimu.spring.complete.guide.service.random.RandomCreateUserCommand;
import com.arturfrimu.spring.complete.guide.service.transactional.propagation.CreateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserService createUserService;

    @Test
    void testCreateUserValid() {
        var command = RandomCreateUserCommand.builder().build().get();
        var actual = new User(command.phoneNumber(), new UserPersonalInformation(command.username(), command.password()));

        when(userMapper.toUser(command)).thenReturn(actual);

        createUserService.create(command);

        verify(userRepository).save(actual);
    }

    @Test
    void testCreateUserInvalidPhoneNumber() {
        var command = RandomCreateUserCommand.builder().phoneNumber(null).build().get();
        var exception = assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
        assertEquals(format("Phone number can not be blank %s", command.phoneNumber()), exception.getMessage());
    }

    @Test
    void testCreateUserInvalidUsername() {
        var command = RandomCreateUserCommand.builder().username(null).build().get();
        var exception = assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
        assertEquals(format("Username can not be blank %s", command.username()), exception.getMessage());
    }


    @Test
    void testCreateUserInvalidPassword() {
        var command = RandomCreateUserCommand.builder().password(null).build().get();
        var exception = assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
        assertEquals(format("Password can not be blank %s", command.password()), exception.getMessage());
    }
}