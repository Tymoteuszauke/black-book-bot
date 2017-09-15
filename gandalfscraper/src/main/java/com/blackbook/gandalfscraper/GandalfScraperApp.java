package com.blackbook.gandalfscraper;

import com.blackbook.gandalfscraper.scraper.LastPageChecker;
import com.blackbook.gandalfscraper.scraper.Scraper;
import com.blackbook.gandalfscraper.webconnector.JsoupWebConnector;
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
public class GandalfScraperApp {

    @Bean
    public ScheduledExecutorService schedulerService(){
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public BotService scrapperService(){
        return new CrawlerScraperService(getCollector(), schedulerService());
    }

    private Collector getCollector(){
        return new Scraper(new JsoupWebConnector(), new LastPageChecker());
    }

    public static void main(String[] args) {
        SpringApplication.run(GandalfScraperApp.class, args);
    }
}
