package com.blackbook.botpersistence.service;

import com.blackbook.botpersistence.dao.AuthorsRepository;
import com.blackbook.botpersistence.dao.BooksRepository;
import com.blackbook.botpersistence.dao.BookstoresRepository;
import com.blackbook.botpersistence.dao.PromotionsRepository;
import com.blackbook.botpersistence.model.Author;
import com.blackbook.botpersistence.model.Book;
import com.blackbook.botpersistence.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import view.creation_model.AuthorCreationData;
import view.creation_model.BookCreationData;
import view.creation_model.PromotionCreationData;

import java.util.stream.Collectors;

@Service
public class PromotionParserService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BookstoresRepository bookstoresRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private PromotionsRepository promotionsRepository;

    /**
     * Parser API (from promotionCreationData into Promotion entity)
     * Expected behavior is if promotion with given book id and given bookstore id is found, it deletes it and
     * saves new promotion
     */
    public Promotion parsePromotionCreationData(PromotionCreationData promotionCreationData) {
        Promotion promotion = new Promotion();
        promotion.setPrice(promotionCreationData.getPrice());
        promotion.setPromotionDetails(promotionCreationData.getPromotionDetails());
        Book parsedBook = parseBookCreationData(promotionCreationData.getBookCreationData());
        Book book = booksRepository.findByTitle(parsedBook.getTitle());
        if (book == null) {
            book = parsedBook;
        }
        promotion.setBook(book);

        //TODO remove if clause since creation data without bookstore id will not be permitted
        if (promotionCreationData.getBookstoreId() != null) {
            promotion.setBookstore(bookstoresRepository.findOne((long) promotionCreationData.getBookstoreId()));

            Promotion existingPromotion = promotionsRepository.findByBookIdAndBookstoreId(book.getId(), promotionCreationData.getBookstoreId());
            if (existingPromotion != null) {
                promotionsRepository.delete(existingPromotion);
            }
        }

        return promotion;
    }

    private Book parseBookCreationData(BookCreationData bookCreationData) {
        Book book = new Book();
        book.setTitle(bookCreationData.getTitle());
        book.setSubtitle(bookCreationData.getSubtitle());
        book.setGenre(bookCreationData.getGenre());
        book.setAuthors(bookCreationData
                .getAuthors()
                .stream()
                .map(this::parseAuthorCreationData)
                .collect(Collectors.toList()));
        return book;
    }

    private Author parseAuthorCreationData(AuthorCreationData authorCreationData) {
        Author author = authorsRepository.findAuthorByNameAndSurname(authorCreationData.getName(), authorCreationData.getSurname());
        if (author == null) {
            author = new Author();
        }
        author.setName(authorCreationData.getName());
        author.setSurname(authorCreationData.getSurname());
        return author;
    }
}
