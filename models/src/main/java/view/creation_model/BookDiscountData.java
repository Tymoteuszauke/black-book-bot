package view.creation_model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by tymek on 25.08.17.
 */
@Data
@Builder
public class BookDiscountData {
    private String bookDiscountDetails;
    private Double price;
    private Integer bookstoreId;
    private BookData bookData;
}
