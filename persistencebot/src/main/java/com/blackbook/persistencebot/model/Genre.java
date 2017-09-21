package com.blackbook.persistencebot.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Entity
@Table(name = "genres")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "books"})
public class Genre {

    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "genres")
    private List<Book> books = new ArrayList<>();

    public Genre(String name) {
        this.name = name;
    }
}
