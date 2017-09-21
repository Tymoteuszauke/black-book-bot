package com.blackbook.persistencebot.dao;

import com.blackbook.persistencebot.model.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByName(String genre);
    List<Genre> findAll();
}
