package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.excel.repository") // Repository 스캔
@EntityScan(basePackages = "com.example.excel.entity") // Entity 스캔
@ComponentScan(basePackages = "com.example.excel")
public class ExcelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
    }
}
