package com.blackbook.botrest.model.creation_model;

import com.blackbook.botrest.model.creation_model.AuthorCreationData;
import lombok.Data;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@Data
public class BookCreationData {
    private String title;
    private String subtitle;
    private String genre;
    private PromotionCreationData promotion;
    private List<AuthorCreationData> authors;
}
