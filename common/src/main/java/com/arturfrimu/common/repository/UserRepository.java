package com.arturfrimu.common.repository;

import com.arturfrimu.common.entity.User;
import com.arturfrimu.common.entity.UserPersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPersonalInformation(UserPersonalInformation personalInformation);

    Optional<User> findByPersonalInformationUsername(String username);

    Optional<User> findByPersonalInformationPassword(String password);
}
