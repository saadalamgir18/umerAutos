package com.example.umerautos.integrationTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    protected String baseUrl = "http://localhost";

    protected static RestTemplate restTemplate;

    @BeforeAll
    public static void initRestTemplate() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUpUrl() {
        baseUrl = "http://localhost:" + port + "/api/v1";
    }
}
