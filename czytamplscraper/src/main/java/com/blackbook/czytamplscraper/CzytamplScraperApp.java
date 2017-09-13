package com.blackbook.czytamplscraper;

import com.blackbook.czytamplscraper.scraper.BookBuilder;
import com.blackbook.czytamplscraper.scraper.BookstoreReader;
import com.blackbook.czytamplscraper.scraper.Connector;
import com.blackbook.czytamplscraper.scraper.PromotionsPageReader;
import com.blackbook.czytamplscraper.scraper.Scraper;
import core.BotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import service.CrawlerScraperService;

@SpringBootApplication
@EnableAsync
public class CzytamplScraperApp {
    public static void main(String[] args) {
        SpringApplication.run(CzytamplScraperApp.class, args);
    }

    @Bean
    public BotService scrapperService() {
        return new CrawlerScraperService(new Scraper(new Connector(), new BookstoreReader(), new PromotionsPageReader(), new BookBuilder()));
    }
}