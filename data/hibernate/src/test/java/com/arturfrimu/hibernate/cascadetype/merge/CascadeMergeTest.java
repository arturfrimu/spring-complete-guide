package com.arturfrimu.hibernate.cascadetype.merge;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CascadeMergeTest {

    @Autowired
    private MergeOrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void whenMergingOrder_thenCascadeMergeUpdatesChildren() {
        // 1) Create and persist an order with two items
        OrderItemMerge item1 = OrderItemMerge.builder()
                .product("Widget")
                .quantity(5)
                .build();
        OrderItemMerge item2 = OrderItemMerge.builder()
                .product("Gadget")
                .quantity(3)
                .build();

        OrderMerge order = OrderMerge.builder()
                .customer("Acme Corp")
                .build();
        order.addItem(item1);
        order.addItem(item2);

        order = orderRepository.saveAndFlush(order);
        Long orderId = order.getId();

        // 2) Detach everything to simulate a disconnected (detached) object graph
        entityManager.clear();

        // 3) Recreate a “detached” version of the order and its items:
        //    – Update the customer name
        //    – Change quantity of the first item
        //    – Remove the second item
        //    – Add a brand-new third item
        OrderMerge detached = new OrderMerge();
        detached.setId(orderId);
        detached.setCustomer("Acme Corp – UPDATED");

        // existing item1 with modified quantity
        OrderItemMerge updatedItem1 = OrderItemMerge.builder()
                .id(item1.getId())
                .product("Widget")
                .quantity(42)
                .build();
        updatedItem1.setOrderMerge(detached);

        // new item3
        OrderItemMerge newItem = OrderItemMerge.builder()
                .product("Thingamajig")
                .quantity(7)
                .build();
        newItem.setOrderMerge(detached);

        // attach children
        detached.addItem(updatedItem1);
        detached.addItem(newItem);
        // (we simply omit item2 to have it removed)

        // 4) Merge back into the persistence context
        OrderMerge merged = orderRepository.saveAndFlush(detached);

        // 5) Fetch fresh from DB and assert:
        OrderMerge reloaded = orderRepository.findById(merged.getId()).orElseThrow();
        assertEquals("Acme Corp – UPDATED", reloaded.getCustomer());

        // Should have exactly 2 items: updatedWidget and the new Thingamajig
        assertEquals(2, reloaded.getItems().size());

        assertTrue(
                reloaded.getItems().stream()
                        .anyMatch(i -> "Widget".equals(i.getProduct()) && i.getQuantity() == 42),
                "Existing item should be updated"
        );
        assertTrue(
                reloaded.getItems().stream()
                        .anyMatch(i -> "Thingamajig".equals(i.getProduct()) && i.getQuantity() == 7),
                "New item should be inserted"
        );
        assertFalse(
                reloaded.getItems().stream()
                        .anyMatch(i -> "Gadget".equals(i.getProduct())),
                "Removed item should no longer exist"
        );
    }
}
