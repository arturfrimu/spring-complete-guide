package com.arturfrimu.spring.complete.guide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class SpringCompleteGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCompleteGuideApplication.class, args);
    }
}
