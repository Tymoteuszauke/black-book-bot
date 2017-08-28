package com.blackbook.botrest.model.creation_model;

import lombok.Data;

import java.util.List;


@Data
public class BookCreationData {
    private String title;
    private String subtitle;
    private String genre;

    private PromotionCreationData promotion;
    private List<AuthorCreationData> authors;
}
