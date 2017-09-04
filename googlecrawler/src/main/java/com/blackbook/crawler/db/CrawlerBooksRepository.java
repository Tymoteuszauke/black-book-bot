package com.blackbook.crawler.db;

import com.blackbook.crawler.db.model.Book;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Siarhei Shauchenka
 * @since 22.08.17
 */
public interface CrawlerBooksRepository extends CrudRepository<Book, Long> {
}
