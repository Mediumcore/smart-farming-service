package com.sdpm.smart.farming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 智慧农场应用
 *
 * @author rukey
 */
@SpringBootApplication
@EnableCaching
public class SmartFarmingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartFarmingApplication.class, args);
    }
}
