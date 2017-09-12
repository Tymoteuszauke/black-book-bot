package com.blackbook.googlecrawler.impl;

import core.CrawlerActionListener;
import core.ICrawler;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Siarhei Shauchenka at 08.09.17
 */
@Test
public class GoogleCrawlerTest {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String KEY_STRING = "&key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private static final String CRITERIA = "-";

    private ExecutorService mockExecutorService;
    private CrawlerActionListener mockListener;
    private GoogleCrawler crawler;

    @BeforeClass
    public void prepareData(){
        mockExecutorService = Mockito.mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());
        mockListener = Mockito.mock(CrawlerActionListener.class);
        crawler = new GoogleCrawler();
    }

    public void testStart(){
        crawler.start(mockListener, mockExecutorService);
        verify(mockExecutorService, times(1)).execute(any());
    }

    public void testGetRequestParameters(){
        Assert.assertEquals(crawler.getId(), GoogleCrawler.GOOGLE_CRAWLER_ID);
        Assert.assertEquals(crawler.getBaseUrl(), BASE_URL);
        Assert.assertEquals(crawler.getCriteria(), CRITERIA);
    }

}
