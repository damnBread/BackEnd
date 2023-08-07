package com.example.damnbreadback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class DamnBreadBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamnBreadBackApplication.class, args);

    }

}
