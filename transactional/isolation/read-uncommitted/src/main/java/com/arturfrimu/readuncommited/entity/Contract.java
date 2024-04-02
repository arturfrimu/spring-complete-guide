package com.arturfrimu.readuncommited.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Setter
@Entity
@Table(name = "CONTRACT")
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class Contract {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @CreationTimestamp
    @Column(name = "createdAt", nullable = false)
    private Timestamp timestamp;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
