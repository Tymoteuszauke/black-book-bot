package com.blackbook.persistencebot.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "endpoints.persistence-api = http://localhost:10900")
public class BookDiscountsControllerTestIT {

    @LocalServerPort
    private int port;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(10900));

    @Test
    public void shouldPostCzytamplaScraper() {
        String persistencebotEndpoint = "/api/book-discounts";
        stubFor(post(urlEqualTo(persistencebotEndpoint))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)));

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:10900" + persistencebotEndpoint)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
