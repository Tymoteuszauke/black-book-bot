
package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.BooksRepository;
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
import static org.hamcrest.Matchers.*;

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
                .body("content.author",
                        hasItems("Tymke Wergiliusz", "Tymek Robert", "Kamil Jurand", "Macko Zdybko")) //defined in resources
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getBooksInPriceRangeExpectBooksFromDummyData() {

        Double priceFrom = 80.00;
        Double priceTo = 130.00;

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("priceFrom", priceFrom)
                .queryParam("priceTo", priceTo)
                .when()
                .get(BOOKS_API)
                .then()
                .body("content.author",
                        hasItems("Tymek Robert", "Kamil Jurand"))
                .body("content.author",
                        not(hasItems("Tymke Wergiliusz", "Macko Zdybko")))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getBooksByAuthorFragmentExpectBooksFromDummyData() {

        String authorFragment = "Tym";

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("query", authorFragment)
                .when()
                .get(BOOKS_API)
                .then()
                .body("content.author",
                        hasItems("Tymek Robert", "Tymke Wergiliusz"))
                .body("content.author",
                        not(hasItems("Kamil Jurand", "Macko Zdybko")))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getBooksByTitleFragmentExpectBooksFromDummyData() {

        String titleFragment = "topor";

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("query", titleFragment)
                .when()
                .get(BOOKS_API)
                .then()
                .body("content.title",
                        hasItems("Scala dla topornych", "Jak uszyc toporek"))
                .body("content.title",
                        not(hasItems("Skame", "Emaks")))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getDummyBookById() {

        long tymkeWergiliuszId = 1;
        String tymkeWergiliuszAuthor = "Tymke Wergiliusz";

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", tymkeWergiliuszId)
                .when()
                .get(BOOKS_API + "/{id}")
                .then()
                .body("author", equalTo(tymkeWergiliuszAuthor))
                .statusCode(HttpStatus.SC_OK);
    }
}


//
//    @Test
//    public void postBookExpectCreatedBook() {
//
//        Book book = new Book();
//        book.setAuthor("Tymoteusz");
//        book.setPrice(65.99);
//        book.setTitle("Nero");
//
//        given()
//                .port(port)
//                .contentType(ContentType.JSON)
//                .body(book)
//                .when()
//                .post(BOOKS_API)
//                .then()
//                .statusCode(HttpStatus.SC_OK);
//
//        Book givenBook = booksRepository.findOne(5L);
//        book.setId(5);
//
//        assertEquals(book, givenBook);


//    @Test
//    public void postBookExpectCreatedBook() {
//
//        String author = "Tymoteusz";
//        String title = "Nero";
//        Double price = 65.99;
//
//        Book book = new Book();
//        book.setAuthor(author);
//        book.setTitle(title);
//        book.setPrice(price);
//
//        given()
//                .port(port)
//                .contentType(ContentType.JSON)
//                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE)))
//                .body(book)
//                .when()
//                .post(BOOKS_API)
//                .then()
//                .body("author", equalTo(author))
//                .body("title", equalTo(title))
//                .body("price", equalTo(price))
//                .statusCode(HttpStatus.SC_OK);
//
//        Pageable pageable = mock(Pageable.class);
//        Page<Book> returnedBook = booksRepository.findBooksWithTextualSearch(title, pageable);
//        Book bookFromRepo = returnedBook.getContent()
//                .stream()
//                .findFirst()
//                .get();
//
//        book.setId(bookFromRepo.getId());
//        assertEquals(book, bookFromRepo);
//    }
