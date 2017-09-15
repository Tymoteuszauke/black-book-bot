package com.blackbook.czytamplscraper;

import com.blackbook.czytamplscraper.scraper.*;
import com.blackbook.utils.core.BotService;
import com.blackbook.utils.core.Collector;
import com.blackbook.utils.service.CrawlerScraperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
    public ScheduledExecutorService schedulerService(){
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(getCollector(), schedulerService());
    }

    private Collector getCollector(){
        return new Scraper(new Connector(), new BookstoreReader(), new PromotionsPageReader(), new BookBuilder());
    }
}