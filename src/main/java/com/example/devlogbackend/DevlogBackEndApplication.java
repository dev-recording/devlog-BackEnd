package com.example.devlogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.devlogbackend.repository")
@EntityScan(basePackages = "com.example.devlogbackend.entity")
public class DevlogBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlogBackEndApplication.class, args);
    }

}
