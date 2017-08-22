package com.blackbook.crawler.db;

import com.blackbook.crawler.db.model.Book;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;


/**
 * @author Siarhei Shauchenka
 * @since 22.08.17
 */
public interface CrawlerBooksRepository extends CrudRepository<Book, Long> {
}
