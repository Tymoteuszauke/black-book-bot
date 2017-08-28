package com.blackbook.dao.dao;


import com.blackbook.dao.model.Bookstore;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tymek on 25.08.17.
 */
public interface BookstoresRepository extends CrudRepository<Bookstore, Long> {
}
