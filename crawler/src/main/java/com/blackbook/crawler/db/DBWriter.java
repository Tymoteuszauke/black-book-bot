package com.blackbook.crawler.db;


import com.blackbook.crawler.db.model.Book;
import com.blackbook.crawler.db.model.BookCreationData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class DBWriter {

    private CrawlerBooksRepository booksRepository;

    public void setBooksRepository(CrawlerBooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public boolean write(BookCreationData bookData) {
        Book book = new Book();
        book.setAuthor(bookData.getAuthor());
        book.setTitle(bookData.getTitle());
        book.setPrice(bookData.getPrice());
        booksRepository.save(book);

        return true;
    }

    public boolean writeAll(List<BookCreationData> booksData) {

        List<Book> books = new ArrayList<>();

        booksData.forEach(data -> {
            Book book = new Book();
            book.setAuthor(data.getAuthor());
            book.setTitle(data.getTitle());
            book.setPrice(data.getPrice());
            books.add(book);
        });
        booksRepository.save(books);

        return true;

    }

}
