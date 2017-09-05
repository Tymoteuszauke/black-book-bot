package view.bookdiscount;

import lombok.Data;

@Data
public class BookDiscountView {

    private long id;
    private Double price;
    private String discountDetails;
    private BookView bookView;
    private BookstoreView bookstoreView;
}
