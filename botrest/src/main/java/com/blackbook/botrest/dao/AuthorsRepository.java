package com.blackbook.botrest.dao;

import com.blackbook.botrest.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorsRepository extends CrudRepository<Author, Long> {


}
