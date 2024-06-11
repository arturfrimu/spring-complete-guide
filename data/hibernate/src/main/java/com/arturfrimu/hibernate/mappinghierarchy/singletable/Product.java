package com.arturfrimu.hibernate.mappinghierarchy.singletable;

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
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.INTEGER) // by default is the entity name. See in subclasses @DiscriminatorValue
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Id needs to be populated manually before persist
    private Long productId;
    private String name;
}
