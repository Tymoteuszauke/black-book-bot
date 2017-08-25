package com.blackbook.scheduler.controller;

import com.blackbook.scheduler.model.ObserverRepository;
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

/**
 * @author Siarhei Shauchenka
 * @since 25.08.17
 */
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:books_controller_test_dummy_data.sql")
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchedulerRestControllerTest {

    private final String ASSIGN_SCHEDULER_API = "/api/scheduler/assign";

    @LocalServerPort
    private int port;

    @Autowired
    private ObserverRepository observerRepository;

    @Test
    public void assignObserver() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .content("{\"url\":\"some test url\"}")
                .when()
                .post(ASSIGN_SCHEDULER_API)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void removeObserver(){

    }

}
