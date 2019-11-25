package com.example.daily_issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CheckListApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckListApplication.class, args);
    }
}
