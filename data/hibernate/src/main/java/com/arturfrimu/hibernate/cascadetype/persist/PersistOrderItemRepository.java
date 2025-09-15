package com.arturfrimu.hibernate.cascadetype.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistOrderItemRepository extends JpaRepository<OrderItemPersist, Long> {
}