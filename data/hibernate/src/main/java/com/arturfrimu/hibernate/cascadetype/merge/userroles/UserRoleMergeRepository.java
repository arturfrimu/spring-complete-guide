package com.arturfrimu.hibernate.cascadetype.merge.userroles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleMergeRepository extends JpaRepository<UserRoleMerge, Long> {
    List<UserRoleMerge> findByUserId(Long id);
}