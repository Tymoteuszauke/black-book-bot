package com.blackbook.googlecrawler.controller;


import com.blackbook.googlecrawler.service.CrawlerService;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "endpoints.persistence-api = http://localhost:11005")
public class CrawlerControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void testPostBookDiscountsRequest(){
        given()
                .port(port)
                .when()
                .post("/api/google-crawler")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testPostBookDiscountsMethodResponse(){
        CrawlerService crawlerService = mock(CrawlerService.class);
        doNothing().when(crawlerService).saveResultsInDatabase();

        CrawlerController crawlerController = new CrawlerController(crawlerService);
        Assert.assertEquals(crawlerController.postBookDiscounts().getCode(), HttpStatus.SC_OK);
    }

}
