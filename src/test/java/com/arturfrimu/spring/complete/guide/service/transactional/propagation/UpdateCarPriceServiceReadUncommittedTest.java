package com.arturfrimu.spring.complete.guide.service.transactional.propagation;

import annotation.ServiceTest;
import com.arturfrimu.spring.complete.guide.entity.Car;
import com.arturfrimu.spring.complete.guide.repository.CarRepository;
import com.arturfrimu.spring.complete.guide.repository.random.RandomCar;
import com.arturfrimu.spring.complete.guide.service.common.MultithreadingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class UpdateCarPriceServiceReadUncommittedTest {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UpdateCarPriceServiceReadUncommitted updateCarPriceServiceReadUncommitted;

    @Test
    void testDirtyRead() throws InterruptedException {
        var initialCar = RandomCar.builder().price(new BigDecimal(10000)).build().get();
        var savedCar = carRepository.save(initialCar);

        Thread increaseThread = new Thread(() -> {
            updateCarPriceServiceReadUncommitted.increase(savedCar.getId(), new BigDecimal(1000));
        });

        increaseThread.setName("increaseThread");

        Thread decreaseThread = new Thread(() -> {
            updateCarPriceServiceReadUncommitted.increase(savedCar.getId(), new BigDecimal(1000));
        });

        decreaseThread.setName("decreaseThread");

        increaseThread.start();
        MultithreadingService.sleep(100);
        decreaseThread.start();

        // Wait for both to finish
        increaseThread.join();
        decreaseThread.join();

        Car car = carRepository.findById(savedCar.getId()).orElseThrow();

        // Check if a dirty read has occurred
        assertEquals(new BigDecimal("12000.00"), car.getPrice());
    }
}