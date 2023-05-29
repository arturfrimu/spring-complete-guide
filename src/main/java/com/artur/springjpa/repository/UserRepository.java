package com.artur.springjpa.repository;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPersonalInformation(UserPersonalInformation personalInformation);

    Optional<User> findByPersonalInformationUsername(String username);

    Optional<User> findByPersonalInformationPassword(String password);
}
