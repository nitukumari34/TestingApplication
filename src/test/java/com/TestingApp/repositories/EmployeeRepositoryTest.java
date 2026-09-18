package com.TestingApp.repositories;

import com.TestingApp.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {

    // Constructor injection is generally preferred in application classes.
// For test classes, field injection with @Autowired is commonly used
// because Spring manages the test instance and injects the required bean.
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
        Employee employee = new Employee(null, "test@example.com", "John Doe", 50000.0);
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findByEmail("test@example.com");

        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertEquals("test@example.com", employees.get(0).getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
        List<Employee> employees = employeeRepository.findByEmail("notfound@example.com");

        assertNotNull(employees);
        assertTrue(employees.isEmpty());
    }
}