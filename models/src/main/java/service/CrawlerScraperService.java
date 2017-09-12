package service;

import callable.SaveBooksCallable;
import callable.SaveBooksCallableDataModel;
import callable.SendLogCallable;
import callable.SendLogCallableDataModel;
import core.BotService;
import core.ICrawler;
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
import view.log.LogEvent;
import view.response.SimpleResponse;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @author Siarhei Shauchenka
 */
@Slf4j
@Service
public class CrawlerScraperService implements BotService{

    private final ScheduledExecutorService scheduledExecutorService;
    private ICrawler crawler;

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @Autowired
    public CrawlerScraperService(ICrawler crawler) {
        this.crawler = crawler;
        scheduledExecutorService = new ScheduledThreadPoolExecutor(4);
    }

    @Async
    @Override
    public void saveResultsInDatabase() {
        final ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        final RestTemplate restTemplate = new RestTemplate(requestFactory);
        final LogEvent.LogEventBuilder logEventBuilder = LogEvent.builder();
        logEventBuilder.startTime(LocalDateTime.now());
        crawler.start(booksData -> {
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

        }, scheduledExecutorService);
    }

    @PreDestroy
    private void terminateExecutor() {
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
