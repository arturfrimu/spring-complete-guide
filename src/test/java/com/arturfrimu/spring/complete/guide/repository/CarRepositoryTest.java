package com.arturfrimu.spring.complete.guide.repository;

import annotation.DatabaseTest;
import com.arturfrimu.spring.complete.guide.repository.random.RandomCar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void testSaveCar() {
        var car = RandomCar.builder().build().get();

        var result = carRepository.saveAndFlush(car);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(car);
    }
}