package com.arturfrimu.spring.complete.guide.repository;

import com.arturfrimu.spring.complete.guide.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {}
