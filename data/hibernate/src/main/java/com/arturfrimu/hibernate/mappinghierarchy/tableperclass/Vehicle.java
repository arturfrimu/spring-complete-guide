package com.arturfrimu.hibernate.mappinghierarchy.tableperclass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Vehicle {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;
    private String manufacturer;
}
