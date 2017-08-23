package com.blackbook.botrest.model;

import lombok.Data;

import java.util.List;

@Data
public class AuthorCreationData {
    private String name;
    private String surname;
    private List<Book> books;
}
