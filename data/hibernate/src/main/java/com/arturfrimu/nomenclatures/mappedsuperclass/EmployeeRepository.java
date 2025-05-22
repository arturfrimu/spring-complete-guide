package com.arturfrimu.nomenclatures.mappedsuperclass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
