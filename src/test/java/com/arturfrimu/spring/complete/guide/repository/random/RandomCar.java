package com.arturfrimu.spring.complete.guide.repository.random;

import com.arturfrimu.spring.complete.guide.entity.Car;
import com.arturfrimu.spring.complete.guide.entity.Car.Color;
import lombok.Builder;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Supplier;

import static com.arturfrimu.spring.complete.guide.entity.Car.Color.RED;

@Builder
public class RandomCar implements Supplier<Car> {

    @Default
    private final Long id = new Random().nextLong();
    @Default
    private final String make = "Mercedes";
    @Default
    private final String model = "E220";
    @Default
    private final BigDecimal price = new BigDecimal(30000);
    @Default
    private final Color color = RED;

    @Override
    public Car get() {
        return new Car(make, model, price, color);
    }
}
