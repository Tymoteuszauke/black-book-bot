package com.blackbook.czytamplscraper;

import com.blackbook.czytamplscraper.scraper.*;
import core.BotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import service.CrawlerScraperService;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class CzytamplScraperApp {
    public static void main(String[] args) {
        SpringApplication.run(CzytamplScraperApp.class, args);
    }

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(new Scraper(new Connector(), new BookstoreReader(), new PromotionsPageReader(), new BookBuilder()));
    }
}