package com.blackbook.botpersistence.dao;


import com.blackbook.botpersistence.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorsRepository extends CrudRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.name LIKE %?1% OR a.surname LIKE %?1%")
    Page<Author> findAuthorsWithTextualSearch(String queryParam, Pageable pageable);
}
