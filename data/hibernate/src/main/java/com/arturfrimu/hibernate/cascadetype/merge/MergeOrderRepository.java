package com.arturfrimu.hibernate.cascadetype.merge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MergeOrderRepository extends JpaRepository<OrderMerge, Long> {
}