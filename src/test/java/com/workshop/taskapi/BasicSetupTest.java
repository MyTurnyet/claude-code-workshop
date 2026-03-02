package com.workshop.taskapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BasicSetupTest {

    @Test
    void contextLoads() {
        // This test verifies that the Spring Boot application context loads successfully
        // If this test passes, your project setup is correct!
        assertTrue(true, "Spring Boot context loaded successfully");
    }

}
