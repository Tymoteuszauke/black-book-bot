package com.blackbook.persistencebot.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Entity
@Table(name = "genres")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id", "books"})
public class Genre {

    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Column(name = "name")
    private final String name;

    @Getter
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "genres")
    private List<Book> books = new ArrayList<>();
}
