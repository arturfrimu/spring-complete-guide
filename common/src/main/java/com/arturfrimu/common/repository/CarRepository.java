package com.arturfrimu.common.repository;

import com.arturfrimu.common.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {}
