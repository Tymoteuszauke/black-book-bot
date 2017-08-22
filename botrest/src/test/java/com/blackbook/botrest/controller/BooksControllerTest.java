package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Book;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by tymek on 21.08.17.
 */

@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:books_controller_test_dummy_data.sql")
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksControllerTest {

    private final String BOOKS_API = "/api/books";

    @LocalServerPort
    private int port;

    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void getBooksExpectBooksFromDummyData() {

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get(BOOKS_API)
                .then()
                .body("content.author[1]", equalTo("Tymek Robert"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void postBookExpectCreatedBook() {

        String author = "Tymoteusz";
        String title = "Nero";
        Double price = 65.99;

        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setPrice(price);

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE)))
                .body(book)
                .when()
                .post(BOOKS_API)
                .then()
                .body("author", equalTo(author))
                .body("title", equalTo(title))
                .body("price", is(price))
                .statusCode(HttpStatus.SC_OK);

        Pageable pageable = mock(Pageable.class);
        Page<Book> returnedBook = booksRepository.findBooksWithTextualSearch(title, pageable);
        Book bookFromRepo = returnedBook.getContent()
                .stream()
                .findFirst()
                .get();

        book.setId(bookFromRepo.getId());
        assertEquals(book, bookFromRepo);
    }

}