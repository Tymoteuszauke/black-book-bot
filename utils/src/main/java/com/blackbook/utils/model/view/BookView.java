package com.blackbook.utils.model.view;

import lombok.Data;

import java.util.List;

/**
 * Created by tymek on 25.08.17.
 */
@Data
public class BookView {
    private long id;
    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String bookPageUrl;
    private String coverUrl;
    private List<GenreView> genreView;
}
