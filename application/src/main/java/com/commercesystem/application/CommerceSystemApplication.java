package com.commercesystem.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.commercesystem")
@EntityScan(basePackages = "com.commercesystem")
@EnableJpaRepositories(basePackages = "com.commercesystem")
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
public class CommerceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceSystemApplication.class, args);
    }

}
