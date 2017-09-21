package com.blackbook.utils.model.creationmodel;

import com.blackbook.utils.model.log.LogEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Siarhei Shauchenka at 12.09.17
 */
@Getter
@Builder
public class SendLogCallableDataModel {
    private final List<BookDiscountData> booksData;
    private final RestTemplate restTemplate;
    private final LogEvent.LogEventBuilder logEventBuilder;
    private final long crawlerId;
    private final String persistenceApiEndpoint;
}
