package com.artur.springjpa.service;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.repository.UserRepository;
import com.artur.springjpa.service.command.CreateUserCommand;
import com.artur.springjpa.service.random.RandomCreateUserCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserService createUserService;

    @Test
    public void testCreateUserValid() {
        var command = RandomCreateUserCommand.builder().build().get();
        createUserService.create(command);
        verify(userRepository).save(new User(command.phoneNumber(), new UserPersonalInformation(command.username(), command.password())));
    }

    @Test
    public void testCreateUserInvalidPhoneNumber() {
        var command = new CreateUserCommand(null, "testUsername", "testPassword");
        assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
    }

    @Test
    public void testCreateUserInvalidUsername() {
        var command = new CreateUserCommand("1234567890", null, "testPassword");
        assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
    }

    @Test
    public void testCreateUserInvalidPassword() {
        var command = new CreateUserCommand("1234567890", "testUsername", null);
        assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
    }
}