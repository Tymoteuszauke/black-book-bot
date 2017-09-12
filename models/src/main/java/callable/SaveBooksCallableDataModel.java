package callable;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;
import view.creationmodel.BookDiscountData;

import java.util.List;

/**
 * @author Siarhei Shauchenka at 12.09.17
 */
@Getter
@Builder
public class SaveBooksCallableDataModel {
    private final List<BookDiscountData> booksData;
    private final RestTemplate restTemplate;
    private final String persistenceApiEndpoint;
}
