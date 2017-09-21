package com.blackbook.utils.callable;

import com.blackbook.utils.model.creationmodel.BookDiscountData;
import com.blackbook.utils.model.creationmodel.SendLogCallableDataModel;
import com.blackbook.utils.model.log.LogEvent;
import com.blackbook.utils.model.response.SimpleResponse;
import lombok.Getter;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author Siarhei Shauchenka at 12.09.17
 */
@Getter
public class SendLogCallable implements Callable<SimpleResponse> {

    private final List<BookDiscountData> booksData;
    private final RestTemplate restTemplate;
    private final LogEvent.LogEventBuilder logEventBuilder;
    private final long crawlerId;
    private final String persistenceApiEndpoint;

    public SendLogCallable(Supplier<SendLogCallableDataModel> dataModelSupplier) {
        this.crawlerId = dataModelSupplier.get().getCrawlerId();
        this.booksData = dataModelSupplier.get().getBooksData();
        this.restTemplate = dataModelSupplier.get().getRestTemplate();
        this.logEventBuilder = dataModelSupplier.get().getLogEventBuilder();
        this.persistenceApiEndpoint = dataModelSupplier.get().getPersistenceApiEndpoint();
    }

    @Override
    public SimpleResponse call() {
        try {
            logEventBuilder.finishTime(LocalDateTime.now());
            logEventBuilder.result(booksData.size());
            logEventBuilder.bookStoreId(crawlerId);
            HttpEntity<Object> logRequest = new HttpEntity<>(logEventBuilder.build());
            return restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts/log", logRequest, SimpleResponse.class);
        } catch (Exception e) {
            return SimpleResponse.builder()
                    .code(HttpStatus.SC_NOT_IMPLEMENTED)
                    .message("Log sending failed, the reason is: " + e.getLocalizedMessage())
                    .build();


        }

    }
}
