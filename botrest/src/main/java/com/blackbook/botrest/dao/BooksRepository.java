package com.blackbook.botrest.dao;

import com.blackbook.botrest.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */
public interface BooksRepository extends CrudRepository<Book, Long> {

    List<Book> findByPriceGreaterThan(double price);
    List<Book> findByPriceLessThan(double price);

    @Query("SELECT b FROM Book b WHERE b.author LIKE %?1% OR b.title LIKE %?1%")
    Page<Book> findBooksWithTextualSearch(String query, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE (b.author LIKE %?1% OR b.title LIKE %?1%) AND (b.price BETWEEN ?2 AND ?3)")
    Page<Book> findBooksWithTextualSearchAndBetweenPrices(String query, Double priceFrom, Double priceTo, Pageable pageable);
}
