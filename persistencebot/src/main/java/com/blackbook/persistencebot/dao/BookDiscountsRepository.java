package com.blackbook.persistencebot.dao;


import com.blackbook.persistencebot.model.BookDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by tymek on 25.08.17.
 */
public interface BookDiscountsRepository extends PagingAndSortingRepository<BookDiscount, Long> {

    List<BookDiscount> findAll();

    @Query(value = "SELECT b FROM BookDiscount b WHERE b.book.title LIKE %?1% OR b.book.authors LIKE %?1%")
    Page<BookDiscount> findAllTextualSearch(String query, Pageable pageable);

    @Query(value = "SELECT b FROM BookDiscount b WHERE (b.book.title LIKE %?1%) AND (b.price BETWEEN ?2 AND ?3)")
    List<BookDiscount> findAllTextualSearchBetweenPrices(String query, Double priceFrom, Double priceTo);

    BookDiscount findByBookIdAndBookstoreId(long id, long bookstoreId);
}
