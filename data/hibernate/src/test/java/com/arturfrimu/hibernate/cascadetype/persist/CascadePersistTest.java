package com.arturfrimu.hibernate.cascadetype.persist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CascadePersistTest {

    @Autowired
    private PersistOrderRepository orderRepository;

    @Test
    void whenSavingOrder_thenItemsAreAlsoPersisted() {
        // 1. Create parent
        OrderPersist orderPersist = new OrderPersist();
        orderPersist.setCustomer("Alice");

        // 2. Create two children
        OrderItemPersist pencil = new OrderItemPersist();
        pencil.setProduct("Pencil");
        pencil.setQuantity(3);

        OrderItemPersist notebook = new OrderItemPersist();
        notebook.setProduct("Notebook");
        notebook.setQuantity(2);

        // 3. Attach children to parent
        orderPersist.addItem(pencil);
        orderPersist.addItem(notebook);

        // 4. Save ONLY the parent
        OrderPersist saved = orderRepository.save(orderPersist);

        // 5. Assertions: parent + both items must have IDs now
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getItems())
                .extracting(OrderItemPersist::getId)
                .allMatch(Objects::nonNull);
    }
}
