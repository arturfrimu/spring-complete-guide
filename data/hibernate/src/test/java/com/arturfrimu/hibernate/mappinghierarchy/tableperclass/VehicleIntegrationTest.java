package com.arturfrimu.hibernate.mappinghierarchy.tableperclass;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VehicleIntegrationTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void test() {
        saveCar();
        saveTruck();
    }

    void saveCar() {
        Car car = Car.builder()
                .manufacturer("Toyota")
                .seatingCapacity(5)
                .build();

        vehicleRepository.save(car);
    }

    void saveTruck() {
        Truck truck = Truck.builder()
                .manufacturer("Volvo")
                .loadCapacity(10000.5)
                .build();
        vehicleRepository.save(truck);
    }

    private static long getRandomLong(final int count) {
        return Long.parseLong(RandomStringUtils.randomNumeric(count));
    }
}