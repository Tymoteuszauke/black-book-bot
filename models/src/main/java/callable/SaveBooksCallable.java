package callable;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import view.creationmodel.BookDiscountData;
import view.response.SimpleResponse;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author Siarhei Shauchenka at 12.09.17
 */
@Slf4j
public class SaveBooksCallable implements Callable<SimpleResponse> {

    private final List<BookDiscountData> booksData;
    private final RestTemplate restTemplate;
    private final String persistenceApiEndpoint;

    public SaveBooksCallable(Supplier<SaveBooksCallableDataModel> dataModelSupplier) {
        this.booksData = dataModelSupplier.get().getBooksData();
        this.restTemplate = dataModelSupplier.get().getRestTemplate();
        this.persistenceApiEndpoint = dataModelSupplier.get().getPersistenceApiEndpoint();
    }

    @Override
    public SimpleResponse call() {
        try {
            HttpEntity<Object> request = new HttpEntity<>(booksData);
            return restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, SimpleResponse.class);
        } catch (Exception e) {
            return SimpleResponse.builder()
                    .code(HttpStatus.SC_NOT_IMPLEMENTED)
                    .message("Books saving failed, the reason is: " + e.getLocalizedMessage())
                    .build();
        }

    }
}
