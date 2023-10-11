package com.arturfrimu.spring.complete.guide.repository;

import com.arturfrimu.spring.complete.guide.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountName(String accountName);
}
