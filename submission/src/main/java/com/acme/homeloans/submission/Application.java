package com.acme.homeloans.submission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring boot application runner
 */
@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.acme.homeloans")
@ComponentScan(basePackages = "com.acme.homeloans")
@EntityScan(basePackages = "com.acme.homeloans")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
