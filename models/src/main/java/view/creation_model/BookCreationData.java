package view.creation_model;

import lombok.Data;

import java.util.List;


@Data
public class BookCreationData {
    private String title;
    private String subtitle;
    private String genre;
    private List<AuthorCreationData> authors;
}
