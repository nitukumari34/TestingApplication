package com.TestingApp;

import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

// Note: @AutoConfigureWebTestClient was removed in Spring Boot 4.x.
// For servlet (MVC) apps, WebTestClient must be built manually via bindToServer().
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    protected WebTestClient webTestClient;

    protected Employee testEmployee = Employee.builder()
            .email("aarav123@gmail.com")
            .name("Aarav")
            .salary(20000)
            .build();

    protected EmployeeDTO testEmployeeDto = EmployeeDTO.builder()
            .email("aarav123@gmail.com")
            .name("Aarav")
            .salary(20000)
            .build();

    @BeforeEach
    void setUpBase() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/api")
                .responseTimeout(java.time.Duration.ofSeconds(100))
                .build();
    }
}
