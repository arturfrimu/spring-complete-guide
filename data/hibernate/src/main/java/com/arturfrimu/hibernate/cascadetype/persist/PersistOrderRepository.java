package com.arturfrimu.hibernate.cascadetype.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistOrderRepository extends JpaRepository<OrderPersist, Long> {
}