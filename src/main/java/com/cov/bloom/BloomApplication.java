package com.cov.bloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BloomApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomApplication.class, args);
    }

}
