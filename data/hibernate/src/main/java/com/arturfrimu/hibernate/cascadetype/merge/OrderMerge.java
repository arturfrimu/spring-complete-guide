package com.arturfrimu.hibernate.cascadetype.merge;

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
public class OrderMerge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;

    @OneToMany(
            mappedBy = "orderMerge",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    private List<OrderItemMerge> items = new ArrayList<>();

    // convenience method
    public void addItem(OrderItemMerge item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setOrderMerge(this);
    }
}

