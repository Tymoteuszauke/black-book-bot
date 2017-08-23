package com.blackbook.botrest.model;

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
    private List<AuthorCreationData> authors;
}
