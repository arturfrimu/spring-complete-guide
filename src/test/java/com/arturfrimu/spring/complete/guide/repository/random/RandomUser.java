package com.arturfrimu.spring.complete.guide.repository.random;

import com.arturfrimu.spring.complete.guide.entity.User;
import com.arturfrimu.spring.complete.guide.entity.UserPersonalInformation;
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
