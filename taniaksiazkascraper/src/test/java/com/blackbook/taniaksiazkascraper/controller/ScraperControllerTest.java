package com.blackbook.taniaksiazkascraper.controller;


import com.blackbook.utils.core.BotService;
import com.blackbook.utils.model.response.SimpleResponse;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScraperControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    public BotService scraperService;

    @Test
    public void shouldPostTaniaksiazkaScraper() {
        // Given
        String taniaksiazkaEndpointUrl = "/api/taniaksiazka-scraper";
        doNothing().when(scraperService).saveResultsInDatabase();

        // When
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .post(taniaksiazkaEndpointUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);

        // Then
        verify(scraperService, times(1)).saveResultsInDatabase();
    }

    @Test
    public void shouldReturnProperResponse() throws IOException {
        // Given
        doNothing().when(scraperService).saveResultsInDatabase();
        ScraperController controller = new ScraperController(scraperService);

        // When
        SimpleResponse getResponse = controller.postBookDiscounts();

        // Then
        Assert.assertEquals(HttpStatus.SC_OK, getResponse.getCode());
    }
}
