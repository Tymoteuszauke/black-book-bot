package view.promotion;

import lombok.Data;

@Data
public class PromotionView {

    private long id;
    private Double price;
    private String promotionDetails;
    private BookView bookView;
    private BookstoreView bookstoreView;

}
