package com.blackbook.botrest.dao;

import com.blackbook.botrest.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */
public interface BooksRepository extends PagingAndSortingRepository<Book, Long> {

//    @Query("SELECT b FROM Book b WHERE b.author.name LIKE %?1% OR b.title LIKE %?1%")
//    Page<Book> findBooksWithTextualSearch(String query, Pageable pageable);

//    @Query("SELECT b FROM Book b WHERE (b.author.name LIKE %?1% OR b.title LIKE %?1%) AND (b.price.price BETWEEN ?2 AND ?3)")
//    Page<Book> findBooksWithTextualSearchAndBetweenPrices(String query, Double priceFrom, Double priceTo, Pageable pageable);
    Page<Book> findAll(Pageable pageable);
}
