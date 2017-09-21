package com.blackbook.czytamplscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
@EnableAsync
public class CzytamplScraperApp {

    public static void main(String[] args) {
        SpringApplication.run(CzytamplScraperApp.class, args);
    }

    @Bean
    public ScheduledExecutorService schedulerService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}