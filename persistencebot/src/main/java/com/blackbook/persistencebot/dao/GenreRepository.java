package com.blackbook.persistencebot.dao;

import com.blackbook.persistencebot.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByName(String genre);
}
