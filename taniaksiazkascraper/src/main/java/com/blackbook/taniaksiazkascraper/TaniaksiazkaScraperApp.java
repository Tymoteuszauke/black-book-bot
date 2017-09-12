package com.blackbook.taniaksiazkascraper;

import com.blackbook.taniaksiazkascraper.scraper.Connector;
import com.blackbook.taniaksiazkascraper.scraper.LastPageChecker;
import com.blackbook.taniaksiazkascraper.scraper.PromoDetailsReader;
import com.blackbook.taniaksiazkascraper.scraper.Scraper;
import core.BotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import service.CrawlerScraperService;

@SpringBootApplication
@EnableAsync
public class TaniaksiazkaScraperApp {
    public static void main(String[] args) {
        SpringApplication.run(TaniaksiazkaScraperApp.class, args);
    }

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(new Scraper(new Connector(), new LastPageChecker(), new PromoDetailsReader()));
    }
}
