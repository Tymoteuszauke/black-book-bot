package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Book;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
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
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void postBookExpectCreatedBook() {

        Book book = new Book();
        book.setAuthor("Tymoteusz");
        book.setPrice(65.99);
        book.setTitle("Nero");

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post(BOOKS_API)
                .then()
                .statusCode(HttpStatus.SC_OK);

        Book givenBook = booksRepository.findOne(5L);
        book.setId(5);

        assertEquals(book, givenBook);
    }

}