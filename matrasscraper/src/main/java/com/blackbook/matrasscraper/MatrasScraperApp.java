package com.blackbook.matrasscraper;

import com.blackbook.matrasscraper.htmlprovider.JsoupHTMLDocumentProvider;
import com.blackbook.matrasscraper.scraper.Scraper;
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
public class MatrasScraperApp {
    public static void main(String[] args) {
        SpringApplication.run(MatrasScraperApp.class, args);
    }

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(new Scraper(new JsoupHTMLDocumentProvider()));
    }
}
