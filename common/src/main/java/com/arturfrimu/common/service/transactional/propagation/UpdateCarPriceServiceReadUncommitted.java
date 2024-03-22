package com.arturfrimu.common.service.transactional.propagation;

import com.arturfrimu.common.exception.ResourceNotFoundException;
import com.arturfrimu.common.repository.CarRepository;
import com.arturfrimu.common.service.common.MultithreadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@Service
@RequiredArgsConstructor
public class UpdateCarPriceServiceReadUncommitted {

    private final CarRepository carRepository;

    @Transactional(isolation = READ_UNCOMMITTED)
    public void increase(Long carId, BigDecimal price) {
        var car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: %s".formatted(carId)));

        car.setPrice(car.getPrice().add(price));

        MultithreadingService.sleep(1000);
    }
}
