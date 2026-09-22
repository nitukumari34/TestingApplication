package com.TestingApp;

import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    protected WebTestClient webTestClient;

    protected Employee testEmployee;
    protected EmployeeDTO testEmployeeDto;

    @BeforeEach
    void setUpBase() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/api")
                .build();

        testEmployee = Employee.builder()
                .name("Aarav")
                .email("aarav123@gmail.com")
                .salary(20000)
                .build();

        testEmployeeDto = new EmployeeDTO();
        testEmployeeDto.setName("Aarav");
        testEmployeeDto.setEmail("aarav123@gmail.com");
        testEmployeeDto.setSalary(20000);
    }
}
