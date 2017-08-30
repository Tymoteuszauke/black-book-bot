package com.blackbook.botpersistence.dao;

import com.blackbook.botpersistence.model.Book;
import com.blackbook.botpersistence.model.Bookstore;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tymek on 25.08.17.
 */
public interface BookstoresRepository extends CrudRepository<Bookstore, Long> {
}
