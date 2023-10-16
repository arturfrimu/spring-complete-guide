package com.arturfrimu.spring.complete.guide.service.random;

import com.arturfrimu.spring.complete.guide.entity.Car.Color;
import com.arturfrimu.spring.complete.guide.service.command.CreateCarCommand;
import lombok.Builder;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Supplier;

import static com.arturfrimu.spring.complete.guide.entity.Car.Color.RED;

@Builder
public class RandomCreateCarCommand implements Supplier<CreateCarCommand> {

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
    public CreateCarCommand get() {
        return new CreateCarCommand(make, model, price, color);
    }
}
