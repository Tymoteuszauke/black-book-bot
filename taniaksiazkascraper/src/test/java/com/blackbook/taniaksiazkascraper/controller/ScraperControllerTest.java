package com.blackbook.taniaksiazkascraper.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "endpoints.persistence-api = http://localhost:13003")
public class ScraperControllerTest {

    @LocalServerPort
    private int port;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(13003));

    @org.junit.Test
    public void shouldPostTaniaksiazkaScraper() {
        String czytamplEndpointUrl = "/api/taniaksiazka-scraper";
        stubFor(post(urlEqualTo(czytamplEndpointUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)));

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:13003" + czytamplEndpointUrl)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}