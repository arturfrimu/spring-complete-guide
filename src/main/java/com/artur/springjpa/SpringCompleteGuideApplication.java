package com.artur.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringCompleteGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCompleteGuideApplication.class, args);
    }
}
