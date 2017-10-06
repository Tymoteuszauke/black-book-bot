package com.blackbook.persistencebot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tymek on 23.08.17.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "bookDiscounts"})
@Entity
@Table(name = "bookstores")
@ToString(exclude = {"bookDiscounts"})
public class Bookstore {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @JsonIgnore
    @OneToMany(mappedBy = "bookstore")
    List<BookDiscount> bookDiscounts;
}
