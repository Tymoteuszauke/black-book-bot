package com.blackbook.botrest.model.view.book;

import com.blackbook.botrest.model.Promotion;
import lombok.Data;

/**
 * Created by tymek on 25.08.17.
 */
@Data
public class BookPromotionView {
    private String promotionDetails;
    private Double price;

    public static BookPromotionView fromPromotion(Promotion promotion) {
        BookPromotionView bookPromotionView = new BookPromotionView();
        bookPromotionView.setPrice(promotion.getPrice());
        bookPromotionView.setPromotionDetails(promotion.getPromotionDetails());
        return bookPromotionView;
    }
}
