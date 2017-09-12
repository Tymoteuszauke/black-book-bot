package com.blackbook.matrasscraper.controller;

import com.blackbook.matrasscraper.service.ScraperService;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.times;

/**
 * Created by Patka on 2017-09-01.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "endpoints.persistence-api = http://localhost:11001")
public class ScraperControllerTest {

    @MockBean
    ScraperService scraperService;

    @LocalServerPort
    private int port;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(11001));

    @Test
    public void shouldPostMatrasScraperStub() {
        String matrasEndpointUrl = "/api/matras-scraper";
        stubFor(post(urlEqualTo(matrasEndpointUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)));

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:11001" + matrasEndpointUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldPostMatrasScraper() {
        String matrasEndpointUrl = "/api/matras-scraper";
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
