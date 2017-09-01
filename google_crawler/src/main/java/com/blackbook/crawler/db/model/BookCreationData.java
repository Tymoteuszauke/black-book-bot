package com.blackbook.crawler.db.model;

import lombok.Data;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@Data
public class BookCreationData {
    private String author;
    private String title;
    private Double price;
}
