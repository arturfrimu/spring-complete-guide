package com.arturfrimu.hibernate.cascadetype.merge.userroles;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

    UserMergeRepository userRepo;
    RoleMergeRepository roleMergeRepository;


    @Transactional
    public UserMerge create(UserMerge user) {
        userRepo.save(user);

        return user;
    }

    @Transactional
    public UserMerge create2(Long id) {
        UserMerge user = UserMerge.builder()
                .id(id)
                .name("User")
                .build();

        userRepo.save(user);

        return user;
    }
}
