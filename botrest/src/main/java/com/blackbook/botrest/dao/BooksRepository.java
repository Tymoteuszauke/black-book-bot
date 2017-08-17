package com.blackbook.botrest.dao;

import com.blackbook.botrest.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */
public interface BooksRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
    List<Book> findAllByAuthor(String author);
    List<Book> findAllByTitle(String title);
    List<Book> findAllByTitleContaining(String titleFragment);
    List<Book> findByPriceGreaterThan(double price);
    List<Book> findByPriceLessThan(double price);
    List<Book> findByPriceBetween(double minPrice, double maxPrice);
}
