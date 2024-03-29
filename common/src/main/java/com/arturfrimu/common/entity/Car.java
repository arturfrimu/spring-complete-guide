package com.arturfrimu.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "CAR")
@Getter
@Setter
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "MAKE", nullable = false)
    private String make;
    @Column(name = "MODEL", nullable = false)
    private String model;
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;
    @Enumerated(value = STRING)
    private Color color;

    public Car(String make, String model, BigDecimal price, Color color) {
        this.make = make;
        this.model = model;
        this.price = price;
        this.color = color;
    }

    public enum Color {
        RED, BLUE, BLACK
    }
}
