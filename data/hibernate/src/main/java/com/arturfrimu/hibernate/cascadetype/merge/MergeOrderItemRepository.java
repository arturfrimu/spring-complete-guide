package com.arturfrimu.hibernate.cascadetype.merge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MergeOrderItemRepository extends JpaRepository<OrderItemMerge, Long> {
}