package com.blackbook.utils.view.creationmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookData {
    private String title;
    private String subtitle;
    private String genre;
    private String authors;
    private String publisher;
    private String bookPageUrl;
    private String coverUrl;
}
