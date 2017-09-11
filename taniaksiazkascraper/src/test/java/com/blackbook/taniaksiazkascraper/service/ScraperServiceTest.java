package com.blackbook.taniaksiazkascraper.service;

import com.blackbook.taniaksiazkascraper.scraper.Scraper;
import org.springframework.web.client.RestOperations;
import org.testng.annotations.Test;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScraperServiceTest {


    @Test
    public void shouldSaveResultsInDatabase() throws Exception {
        // Given
        List<BookDiscountData> discountDataList = new LinkedList<>();
        BookDiscountData discountData = BookDiscountData.builder()
                .bookstoreId(2)
                .price(24.99)
                .bookDiscountDetails("-60%")
                .build();
        discountDataList.add(discountData);

        RestOperations restOperations = mock(RestOperations.class);
        when(restOperations.postForObject(anyString(), any(), any())).thenReturn(null);

        Scraper scraper = mock(Scraper.class);
        when(scraper.extractBookElements()).thenReturn(discountDataList);

        ScraperService scraperService = new ScraperService(restOperations, scraper);

        // When
        scraperService.saveResultsInDatabase();

        // Then
        verify(restOperations, times(1)).postForObject(anyString(), any(), any());
    }
}