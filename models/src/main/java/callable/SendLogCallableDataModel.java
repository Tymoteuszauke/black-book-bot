package callable;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;
import view.creationmodel.BookDiscountData;
import view.log.LogEvent;

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
