package com.blackbook.persistencebot;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.testng.annotations.Test;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import static org.testng.Assert.*;

public class PersistenceApplicationTest {

    private PersistenceApplication application = new PersistenceApplication();

    @Test
    public void shouldCreateH2ServletRegistrationBean() throws Exception {
        // When
        ServletRegistrationBean h2Bean = application.h2ServletRegistration();

        // Then
        assertTrue(h2Bean.getUrlMappings().contains("/console/*"));
    }

    @Test
    public void shouldCreateDocketBean() throws Exception {
        // When
        Docket docket = application.api();

        // Then
        assertEquals("Persistence API", docket.getGroupName());
    }

    @Test
    public void shouldCreateApiInfoObject() {
        // When
        ApiInfo apiInfo = application.apiInfo();

        // Then
        assertEquals("Black Book Bot REST API for Persistence Bot module", apiInfo.getTitle());
        assertEquals("Methods for DB manipulations", apiInfo.getDescription());
    }

    @Test
    public void shouldCreateWebMvcConfigurerBean() throws Exception {
        // When
        WebMvcConfigurer webMvcConfigurer = application.corsConfigurer();

        // Then
        assertNotNull(webMvcConfigurer);
    }
}