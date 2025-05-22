package com.arturfrimu.nomenclatures.mappedsuperclass.service;

import com.arturfrimu.nomenclatures.mappedsuperclass.Employee;
import com.arturfrimu.nomenclatures.mappedsuperclass.EmployeeRepository;
import com.arturfrimu.nomenclatures.mappedsuperclass.Manager;
import com.arturfrimu.nomenclatures.mappedsuperclass.ManagerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @Test
    void test() {
        Employee savedEmployee = saveEmployee();
        Manager savedManager = saveManager();

        findEmployee(savedEmployee.getPersonId());
        findManager(savedManager.getPersonId());
    }

    Employee saveEmployee() {
        Employee employee = Employee.builder()
                .name("Employee Name - " + RandomStringUtils.randomAlphabetic(10))
                .company("Employee Company - " + RandomStringUtils.randomAlphabetic(10))
                .build();
        return employeeRepository.save(employee);
    }

    Manager saveManager() {
        Manager manager = Manager.builder()
                .name("Manager Name - " + RandomStringUtils.randomAlphabetic(10))
                .department("Manager Department - " + RandomStringUtils.randomAlphabetic(10))
                .build();
        return managerRepository.save(manager);
    }

    Manager findManager(final long id) {
        return managerRepository.findById(id).orElseThrow();
    }

    Employee findEmployee(final long id) {
        return employeeRepository.findById(id).orElseThrow();
    }
}