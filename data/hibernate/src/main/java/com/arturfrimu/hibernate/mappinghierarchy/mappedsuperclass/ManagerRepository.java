package com.arturfrimu.hibernate.mappinghierarchy.mappedsuperclass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
