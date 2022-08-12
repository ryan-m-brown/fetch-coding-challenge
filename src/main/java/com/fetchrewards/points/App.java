package com.fetchrewards.points;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SuppressWarnings("unused")
public class App {

    @EnableAutoConfiguration
    @Configuration
    @ComponentScan({"com.fetchrewards.points"})
    public static class Config {

    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
