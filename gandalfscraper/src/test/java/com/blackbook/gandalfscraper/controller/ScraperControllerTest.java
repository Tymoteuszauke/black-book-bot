package com.blackbook.gandalfscraper.controller;

import com.blackbook.gandalfscraper.service.ScraperService;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScraperControllerTest {

    @MockBean
    ScraperService scraperService;

    @LocalServerPort
    private int port;

    @Test
    public void shouldPostMatrasScraper() {
        String matrasEndpointUrl = "/api/gandalf-scraper";
        Mockito.doNothing().when(scraperService).saveResultsInDatabase();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .post(matrasEndpointUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);

        Mockito.verify(scraperService, times(1)).saveResultsInDatabase();
    }
}
