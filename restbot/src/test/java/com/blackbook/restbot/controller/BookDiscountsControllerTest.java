package com.blackbook.restbot.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import view.creationmodel.BookDiscountData;

import java.util.ArrayList;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "endpoints.persistence-api = http://localhost:11000")
public class BookDiscountsControllerTest {

    @LocalServerPort
    private int port;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(11000));

    @Test
    public void postPromotionExpectOk() {
        stubFor(post(urlEqualTo("/api/book-discounts"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)));

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(new ArrayList<>(Collections.singletonList(BookDiscountData.builder().build())))
                .when()
                .post("/api/book-discounts")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}