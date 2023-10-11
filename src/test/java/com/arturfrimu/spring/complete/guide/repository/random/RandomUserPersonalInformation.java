package com.arturfrimu.spring.complete.guide.repository.random;

import com.arturfrimu.spring.complete.guide.entity.UserPersonalInformation;
import lombok.Builder;
import lombok.Builder.Default;

import java.util.function.Supplier;

@Builder
public class RandomUserPersonalInformation implements Supplier<UserPersonalInformation> {

    @Default
    private final String username = "test";
    @Default
    private final String password = "test";

    @Override
    public UserPersonalInformation get() {
        return new UserPersonalInformation(username, password);
    }
}
