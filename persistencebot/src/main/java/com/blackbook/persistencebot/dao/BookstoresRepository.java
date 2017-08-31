package com.blackbook.persistencebot.dao;

import com.blackbook.persistencebot.model.Bookstore;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tymek on 25.08.17.
 */
public interface BookstoresRepository extends CrudRepository<Bookstore, Long> {
}
