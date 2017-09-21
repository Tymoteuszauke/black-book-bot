package com.blackbook.googlecrawler.controller;


import com.blackbook.googlecrawler.service.GoogleCrawlerService;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.*;

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
        GoogleCrawlerService service = mock(GoogleCrawlerService.class);
        doNothing().when(service).saveResultsInDatabase();

        CrawlerController crawlerController = new CrawlerController(service);
        Assert.assertEquals(crawlerController.postBookDiscounts().getCode(), HttpStatus.SC_OK);

        verify(service, times(1)).saveResultsInDatabase();
    }

}
