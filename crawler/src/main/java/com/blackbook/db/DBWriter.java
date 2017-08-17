package com.blackbook.db;

import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Book;
import com.blackbook.botrest.model.BookCreationData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class DBWriter {

    private Lock dbLocker = new ReentrantLock();

    @Autowired
    private BooksRepository booksRepository;

    public boolean write(BookCreationData bookData) {
        if (dbLocker.tryLock()) {
            try {
                Book book = new Book();
                book.setAuthor(bookData.getAuthor());
                book.setTitle(bookData.getTitle());
                book.setPrice(bookData.getPrice());
                booksRepository.save(book);
            } finally {
                dbLocker.unlock();
            }
            return true;
        }
        return false;
    }

    public boolean writeAll(List<BookCreationData> booksData) {
        if (dbLocker.tryLock()) {
            try {
                List<Book> books = new ArrayList<>();

                booksData.forEach(data -> {
                    Book book = new Book();
                    book.setAuthor(data.getAuthor());
                    book.setTitle(data.getTitle());
                    book.setPrice(data.getPrice());
                    books.add(book);
                });
                booksRepository.save(books);
            } finally {
                dbLocker.unlock();
            }
            return true;
        }
        return false;
    }

}
