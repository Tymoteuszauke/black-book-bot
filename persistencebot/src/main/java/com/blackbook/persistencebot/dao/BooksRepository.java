package com.blackbook.persistencebot.dao;

import com.blackbook.persistencebot.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */
public interface BooksRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findAll(Pageable pageable);

    Book findByTitle(String title);
    Book findByTitleAndSubtitle(String title, String subTitle);
}
