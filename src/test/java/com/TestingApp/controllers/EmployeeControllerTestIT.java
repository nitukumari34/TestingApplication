package com.TestingApp.controllers;

import com.TestingApp.AbstractIntegrationTest;
import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.entities.Employee;
import com.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class EmployeeControllerTestIT extends AbstractIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success() {
        Employee savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.get()
                .uri("/api/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    void testGetEmployeeById_Failure() {
        webTestClient.get()
                .uri("/api/employees/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        employeeRepository.save(testEmployee);

        webTestClient.post()
                .uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee() {
        webTestClient.post()
                .uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.put()
                .uri("/api/employees/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateTheEmail_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDto.setName("Random Name");
        testEmployeeDto.setEmail("random@gmail.com");

        webTestClient.put()
                .uri("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateEmployee_whenEmployeeIsValid_thenUpdateEmployee() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDto.setName("Random Name");
        testEmployeeDto.setSalary(250.0);

        webTestClient.put()
                .uri("/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .value(dto -> {
                    assert dto.getName().equals("Random Name");
                    assert dto.getSalary() == 250.0;
                });
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.delete()
                .uri("/api/employees/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee() {
        Employee savedEmployee = employeeRepository.save(testEmployee);

        // First delete — should succeed with 204
        webTestClient.delete()
                .uri("/api/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNoContent();

        // Second delete — employee gone, should return 404
        webTestClient.delete()
                .uri("/api/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}