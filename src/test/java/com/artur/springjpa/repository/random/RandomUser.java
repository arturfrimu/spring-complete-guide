package com.artur.springjpa.repository.random;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import lombok.Builder;
import lombok.Builder.Default;

import java.util.function.Supplier;

@Builder
public class RandomUser implements Supplier<User> {

    @Default
    private final UserPersonalInformation personalInformation = RandomUserPersonalInformation
            .builder()
            .build()
            .get();

    @Default
    private final String phoneNumber = "010-12-34-56";

    @Override
    public User get() {
        return new User(phoneNumber, personalInformation);
    }
}
