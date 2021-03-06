package com.blackbook.czytamplscraper.controller;


import com.blackbook.utils.core.BotService;
import com.blackbook.utils.response.SimpleResponse;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScraperControllerTest {

    @MockBean
    private BotService scraperService;

    @LocalServerPort
    private int port;

    @Test
    public void shouldPostBookDiscounts() throws Exception {
        // Given
        String czytamplEndpointUrl = "/api/czytampl-scraper";
        doNothing().when(scraperService).saveResultsInDatabase();

        // When
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .post(czytamplEndpointUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // Then
        verify(scraperService, times(1)).saveResultsInDatabase();
    }

    @Test
    public void shouldReturnProperResponse() {
        // Given
        doNothing().when(scraperService).saveResultsInDatabase();
        ScraperController controller = new ScraperController(scraperService);

        // When
        ResponseEntity<SimpleResponse<String>> getResponse = controller.postBookDiscounts();

        // Then
        Assert.assertEquals(org.springframework.http.HttpStatus.OK, getResponse.getStatusCode());
    }
}