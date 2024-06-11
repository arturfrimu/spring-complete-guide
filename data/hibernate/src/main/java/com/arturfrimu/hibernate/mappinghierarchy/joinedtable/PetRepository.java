package com.arturfrimu.hibernate.mappinghierarchy.joinedtable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
