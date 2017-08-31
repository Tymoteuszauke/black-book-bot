package view.promotion;


import lombok.Data;

import java.util.Set;

/**
 * Created by tymek on 25.08.17.
 */
@Data
public class BookView {
    private long id;
    private String title;
    private String subtitle;
    private Set<BookAuthorView> authors;
}
