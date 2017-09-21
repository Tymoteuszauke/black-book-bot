package com.blackbook.taniaksiazkascraper.controller;


import com.blackbook.taniaksiazkascraper.service.TaniaksiazkaScraperService;
import com.blackbook.utils.model.response.SimpleResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.mockito.Mockito.*;


public class ScraperControllerTest {

    @MockBean
    TaniaksiazkaScraperService service;


    @Test
    public void shouldPostTaniaksiazkaScraper() throws IOException {
        // Given
        service = mock(TaniaksiazkaScraperService.class);
        ScraperController controller = new ScraperController(service);
        doNothing().when(service).saveResultsInDatabase();

        // When
        controller.postBookDiscounts();

        // Then
        verify(service, times(1)).saveResultsInDatabase();
    }

    @Test
    public void shouldReturnProperResponse() throws IOException {
        // Given
        TaniaksiazkaScraperService scraperService = mock(TaniaksiazkaScraperService.class);
        doNothing().when(scraperService).saveResultsInDatabase();
        ScraperController controller = new ScraperController(scraperService);

        // When
        SimpleResponse getResponse = controller.postBookDiscounts();

        // Then
        Assert.assertEquals(HttpStatus.SC_OK, getResponse.getCode());
    }
}
