package com.arturfrimu.hibernate.cascadetype.persist;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderPersist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;

    // ← only PERSIST is cascaded down to items
    @OneToMany(
            mappedBy = "orderPersist",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<OrderItemPersist> items = new ArrayList<>();

    // convenience method
    public void addItem(OrderItemPersist item) {
        if (item == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setOrderPersist(this);
    }
}

