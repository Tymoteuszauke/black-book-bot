package view.creation_model;

import lombok.Data;

import java.util.List;


@Data
public class BookData {
    private String title;
    private String subtitle;
    private String genre;
    private List<AuthorCreationData> authors;
}
