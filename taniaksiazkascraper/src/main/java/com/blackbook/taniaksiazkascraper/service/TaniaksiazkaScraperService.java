package com.blackbook.taniaksiazkascraper.service;

import com.blackbook.utils.callable.SaveBooksCallable;
import com.blackbook.utils.callable.SendLogCallable;
import com.blackbook.utils.core.BotService;
import com.blackbook.utils.core.Collector;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import com.blackbook.utils.model.creationmodel.SaveBooksCallableDataModel;
import com.blackbook.utils.model.creationmodel.SendLogCallableDataModel;
import com.blackbook.utils.model.log.LogEvent;
import com.blackbook.utils.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
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
@EnableAsync
public class TaniaksiazkaScraperService implements BotService {

    private static final long DELAY_BEFORE_SECOND_TRY = 5;

    private final ScheduledExecutorService scheduledExecutorService;
    private final Collector collector;

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    Consumer<List<BookDiscountData>> collectorConsumer;

    @Autowired
    public TaniaksiazkaScraperService(Collector collector, ScheduledExecutorService scheduledExecutorService) {
        this.collector = collector;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Async
    @Override
    public void saveResultsInDatabase() {
        final ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        final RestTemplate restTemplate = new RestTemplate(requestFactory);
        final LogEvent.LogEventBuilder logEventBuilder = LogEvent.builder();

        logEventBuilder.startTime(LocalDateTime.now());

        collectorConsumer = booksData -> {
            final SaveBooksCallable saveBooksDataCallable = new SaveBooksCallable(() -> SaveBooksCallableDataModel.builder()
                    .booksData(booksData)
                    .persistenceApiEndpoint(persistenceApiEndpoint)
                    .restTemplate(restTemplate)
                    .build());

            try {
                Future<ResponseEntity<SimpleResponse<String>>> saveDataFuture = scheduledExecutorService.submit(saveBooksDataCallable);
                if (saveDataFuture.get().getStatusCode() != HttpStatus.OK) {
                    log.warn("Save data failed. Try again...");
                    saveDataFuture = scheduledExecutorService.schedule(saveBooksDataCallable, DELAY_BEFORE_SECOND_TRY, TimeUnit.SECONDS); // try to save data again
                }
                log.info(saveDataFuture.get().getBody().getResponse());

                if (saveDataFuture.get().getStatusCode() == HttpStatus.OK) {
                    final SendLogCallable sendLogCallable = new SendLogCallable(() -> SendLogCallableDataModel.builder()
                            .booksData(booksData)
                            .crawlerId(collector.getId())
                            .logEventBuilder(logEventBuilder)
                            .persistenceApiEndpoint(persistenceApiEndpoint)
                            .restTemplate(restTemplate)
                            .build());

                    Future<ResponseEntity<SimpleResponse<String>>> sendLogDataFuture = scheduledExecutorService.submit(sendLogCallable);
                    if (sendLogDataFuture.get().getStatusCode() != HttpStatus.OK) {
                        log.warn("Log sending failed. Try again...");
                        sendLogDataFuture = scheduledExecutorService.schedule(sendLogCallable, DELAY_BEFORE_SECOND_TRY, TimeUnit.SECONDS); //try to send log again
                    }
                    log.info(sendLogDataFuture.get().getBody().getResponse());
                }

            } catch (InterruptedException | ExecutionException e) {
                log.error("CrawlerScraperService execution canceled. Reason is: " + e.getLocalizedMessage());
            }
        };

        collector.start(collectorConsumer);
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
