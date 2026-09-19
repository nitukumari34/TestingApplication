package com.TestingApp.repositories;

import com.TestingApp.TestContainerConfiguration;
import com.TestingApp.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestContainerConfiguration.class)
@SpringBootTest
class EmployeeRepositoryTest {

    // Constructor injection is generally preferred in application classes.
    // For test classes, field injection with @Autowired is commonly used
    // because Spring manages the test instance and injects the required bean.
    @Autowired
    private EmployeeRepository employeeRepository;

    // create employee
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .name("Nitu")
                .email("nituspj032001@gmail.com")
                .salary(50000L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        // Arrange,Given
        employeeRepository.save(employee);

        // Act,When
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

        // Assert, Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
        // Given
        String email = "isNotPresent@gmail.com";

        // When
        List<Employee> employeeList = employeeRepository.findByEmail(email);
        // Then
        assertThat(employeeList).isNotNull();
        // assertThat(employeeList).isNotEmpty();
        assertThat(employeeList).isEmpty();

    }
}