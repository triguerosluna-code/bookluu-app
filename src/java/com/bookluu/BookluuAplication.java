package com.bookluu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import java.util.Collections;

@SpringBootApplication
@ComponentScan(basePackages = "com.bookluu")
public class BookluuAplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BookluuAplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }
}