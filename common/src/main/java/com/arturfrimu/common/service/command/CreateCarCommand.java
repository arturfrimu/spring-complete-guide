package com.arturfrimu.common.service.command;


import com.arturfrimu.common.entity.Car;

import java.math.BigDecimal;

public record CreateCarCommand(String make, String model, BigDecimal price, Car.Color color) {
}
