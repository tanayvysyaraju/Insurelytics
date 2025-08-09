package com.insurelytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for the Insurelytics Spring Boot application.
 */
@SpringBootApplication
@EnableScheduling
public class InsurelyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsurelyticsApplication.class, args);
    }
}