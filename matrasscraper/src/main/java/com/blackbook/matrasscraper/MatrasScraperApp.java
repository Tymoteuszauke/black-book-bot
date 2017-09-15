package com.blackbook.matrasscraper;

import com.blackbook.matrasscraper.htmlprovider.JsoupHTMLDocumentProvider;
import com.blackbook.matrasscraper.scraper.Scraper;
import com.blackbook.utils.core.BotService;
import com.blackbook.utils.core.Collector;
import com.blackbook.utils.service.CrawlerScraperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
    public ScheduledExecutorService schedulerService(){
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(getCollector(), schedulerService());
    }

    private Collector getCollector(){
        return new Scraper(new JsoupHTMLDocumentProvider());
    }

}
