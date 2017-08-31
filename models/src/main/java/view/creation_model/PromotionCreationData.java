package view.creation_model;

import lombok.Data;

/**
 * Created by tymek on 25.08.17.
 */
@Data
public class PromotionCreationData {
    private String promotionDetails;
    private Double price;
    private Integer bookstoreId;
    private BookCreationData bookCreationData;
}
