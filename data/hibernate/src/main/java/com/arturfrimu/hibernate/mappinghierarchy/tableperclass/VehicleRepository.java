package com.arturfrimu.hibernate.mappinghierarchy.tableperclass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
