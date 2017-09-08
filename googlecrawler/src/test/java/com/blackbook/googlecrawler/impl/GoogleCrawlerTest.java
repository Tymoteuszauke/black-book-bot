package com.blackbook.googlecrawler.impl;

import com.blackbook.googlecrawler.core.CrawlerActionListener;
import com.blackbook.googlecrawler.core.ICrawler;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
    private ICrawler crawler;

    @BeforeClass
    public void prepareData(){
        mockExecutorService = Mockito.mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());
        mockListener = Mockito.mock(CrawlerActionListener.class);
        crawler = new GoogleCrawler(mockListener, mockExecutorService);
    }

    public void testStart(){
        crawler.start();
        verify(mockExecutorService, times(1)).execute(any());
    }

    public void testGetRequestParameters(){
        Assert.assertEquals(crawler.getId(), GoogleCrawler.class.getSimpleName());
        Assert.assertEquals(crawler.getBaseUrl(), BASE_URL);
        Assert.assertEquals(crawler.getCriteria(), CRITERIA);
    }

}
