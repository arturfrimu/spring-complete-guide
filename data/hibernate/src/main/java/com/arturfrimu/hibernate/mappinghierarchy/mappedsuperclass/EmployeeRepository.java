package com.arturfrimu.hibernate.mappinghierarchy.mappedsuperclass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
