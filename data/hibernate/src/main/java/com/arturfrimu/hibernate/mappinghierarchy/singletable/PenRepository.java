package com.arturfrimu.hibernate.mappinghierarchy.singletable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PenRepository extends JpaRepository<Pen, Long> {
}
