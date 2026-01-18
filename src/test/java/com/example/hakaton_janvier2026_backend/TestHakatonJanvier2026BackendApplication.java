package com.example.hakaton_janvier2026_backend;

import org.springframework.boot.SpringApplication;

public class TestHakatonJanvier2026BackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(HakatonJanvier2026BackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
