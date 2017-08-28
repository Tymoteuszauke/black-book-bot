package com.blackbook.botrest.model.view.promotion;

import com.blackbook.dao.model.Promotion;
import lombok.Data;

@Data
public class PromotionView {

    private long id;
    private Double price;
    private String promotionDetails;
    private BookView bookView;
    private BookstoreView bookstoreView;

    public static PromotionView fromPromotion(Promotion promotion) {
        PromotionView promotionView = new PromotionView();
        promotionView.setId(promotion.getId());
        promotionView.setPrice(promotion.getPrice());
        promotionView.setBookstoreView(BookstoreView.fromBookstore(promotion.getBookstore()));
        promotionView.setPromotionDetails(promotion.getPromotionDetails());
        promotionView.setBookView(BookView.fromBook(promotion.getBook()));

        return promotionView;
    }
}
