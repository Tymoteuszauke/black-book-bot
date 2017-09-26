package com.blackbook.utils.callable;

import com.blackbook.utils.model.creationmodel.BookDiscountData;
import com.blackbook.utils.model.creationmodel.SaveBooksCallableDataModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author Siarhei Shauchenka at 12.09.17
 */
@Slf4j
@Getter
public class SaveBooksCallable implements Callable<ResponseEntity<String>> {

    private final List<BookDiscountData> booksData;
    private final RestTemplate restTemplate;
    private final String persistenceApiEndpoint;

    public SaveBooksCallable(Supplier<SaveBooksCallableDataModel> dataModelSupplier) {
        this.booksData = dataModelSupplier.get().getBooksData();
        this.restTemplate = dataModelSupplier.get().getRestTemplate();
        this.persistenceApiEndpoint = dataModelSupplier.get().getPersistenceApiEndpoint();
    }

    @Override
    public ResponseEntity<String> call() {
        try {
            HttpEntity<Object> request = new HttpEntity<>(booksData);
            return restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, ResponseEntity.class);
        } catch (Exception e) {
            return new ResponseEntity<String>("Books saving failed, the reason is: " + e.getLocalizedMessage(),
                    HttpStatus.NOT_IMPLEMENTED);
        }

    }
}
