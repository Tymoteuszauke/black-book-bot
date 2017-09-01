package view.creation_model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorCreationData {
    private String name;
    private String surname;
}
