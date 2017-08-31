package com.blackbook.persistencebot.dao;


import com.blackbook.persistencebot.model.Promotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by tymek on 25.08.17.
 */
public interface PromotionsRepository extends PagingAndSortingRepository<Promotion, Long> {

    List<Promotion> findAll();

    @Query(value = "SELECT p FROM Promotion p WHERE p.book.title LIKE %?1%")
    List<Promotion> findAllTextualSearch(String query);

    @Query(value = "SELECT p FROM Promotion p WHERE (p.book.title LIKE %?1%) AND (p.price BETWEEN ?2 AND ?3)")
    List<Promotion> findAllTextualSearchBetweenPrices(String query, Double priceFrom, Double priceTo);

    Promotion findByBookIdAndBookstoreId(long id, long bookstoreId);
}
