package com.TestingApp.controllers;

import com.TestingApp.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class EmployeeControllerTestIT {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void contextLoads() {
        // Application context loads successfully
    }

}