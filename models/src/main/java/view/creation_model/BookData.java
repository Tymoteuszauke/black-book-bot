package view.creation_model;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class BookData {
    private String title;
    private String subtitle;
    private String genre;
    private String authors;
}
