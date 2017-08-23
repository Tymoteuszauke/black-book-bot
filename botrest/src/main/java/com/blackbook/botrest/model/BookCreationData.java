package com.blackbook.botrest.model;

import lombok.Data;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@Data
public class BookCreationData {
    private String author;
    private String title;
    private String subtitle;
    private Double price;
}
