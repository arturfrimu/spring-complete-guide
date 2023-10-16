package com.arturfrimu.spring.complete.guide.service.command;

import com.arturfrimu.spring.complete.guide.entity.Car;

import java.math.BigDecimal;

public record CreateCarCommand(String make, String model, BigDecimal price, Car.Color color) {
}
