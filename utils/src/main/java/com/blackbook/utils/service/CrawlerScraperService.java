package com.blackbook.utils.service;

import com.blackbook.utils.callable.SaveBooksCallable;
import com.blackbook.utils.callable.SendLogCallable;
import com.blackbook.utils.core.BotService;
import com.blackbook.utils.core.ICrawler;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import com.blackbook.utils.view.creationmodel.SaveBooksCallableDataModel;
import com.blackbook.utils.view.creationmodel.SendLogCallableDataModel;
import com.blackbook.utils.view.log.LogEvent;
import com.blackbook.utils.view.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


/**
 * @author Siarhei Shauchenka
 */
@Slf4j
@Service
public class CrawlerScraperService implements BotService{

    private final ScheduledExecutorService scheduledExecutorService;
    private final ICrawler crawler;

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    Consumer<List<BookDiscountData>> crawlerConsumer;

    @Autowired
    public CrawlerScraperService(ICrawler crawler)
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(4);
    }

    @Async
    @Override
    public void saveResultsInDatabase() {
        final ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        final RestTemplate restTemplate = new RestTemplate(requestFactory);
        final LogEvent.LogEventBuilder logEventBuilder = LogEvent.builder();

        logEventBuilder.startTime(LocalDateTime.now());

        crawlerConsumer = booksData -> {
            final SaveBooksCallable saveBooksDataCallable = new SaveBooksCallable(() -> SaveBooksCallableDataModel.builder()
                    .booksData(booksData)
                    .persistenceApiEndpoint(persistenceApiEndpoint)
                    .restTemplate(restTemplate)
                    .build());

            try {
                Future<SimpleResponse> saveDataFuture = scheduledExecutorService.submit(saveBooksDataCallable);
                if (saveDataFuture.get().getCode() != HttpStatus.SC_OK) {
                    log.warn("Save data failed. Try again...");
                    saveDataFuture = scheduledExecutorService.schedule(saveBooksDataCallable, 5, TimeUnit.SECONDS); // try to save data again
                }
                log.info(saveDataFuture.get().getMessage());

                if (saveDataFuture.get().getCode() == HttpStatus.SC_OK) {
                    final SendLogCallable sendLogCallable = new SendLogCallable(() -> SendLogCallableDataModel.builder()
                            .booksData(booksData)
                            .crawlerId(crawler.getId())
                            .logEventBuilder(logEventBuilder)
                            .persistenceApiEndpoint(persistenceApiEndpoint)
                            .restTemplate(restTemplate)
                            .build());

                    Future<SimpleResponse> sendLogDataFuture = scheduledExecutorService.submit(sendLogCallable);
                    if (sendLogDataFuture.get().getCode() != HttpStatus.SC_OK) {
                        log.warn("Log sending failed. Try again...");
                        sendLogDataFuture = scheduledExecutorService.schedule(sendLogCallable, 5, TimeUnit.SECONDS); //try to send log again
                    }
                    log.info(sendLogDataFuture.get().getMessage());
                }

            } catch (InterruptedException | ExecutionException e) {
                log.error("CrawlerScraperService execution canceled. Reason is: " + e.getLocalizedMessage());
            }
        };

        crawler.start(crawlerConsumer);
    }

    @PreDestroy
    void terminateExecutor() {
        log.info("Terminating executor.");
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(2, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            scheduledExecutorService.shutdownNow();
        }
    }

}
