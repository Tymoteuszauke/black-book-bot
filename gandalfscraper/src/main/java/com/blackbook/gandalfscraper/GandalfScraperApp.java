package com.blackbook.gandalfscraper;

import com.blackbook.gandalfscraper.scraper.LastPageChecker;
import com.blackbook.gandalfscraper.scraper.Scraper;
import com.blackbook.gandalfscraper.webconnector.JsoupWebConnector;
import core.BotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import service.CrawlerScraperService;

/**
 * @author "Patrycja Zaremba"
 */
@SpringBootApplication
@EnableAsync
public class GandalfScraperApp {

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(new Scraper(new JsoupWebConnector(), new LastPageChecker()));
    }
    public static void main(String[] args) {
        SpringApplication.run(GandalfScraperApp.class, args);
    }
}
