package com.blackbook.googlecrawler.impl;

import core.CrawlerActionListener;
import core.ICrawler;
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

public class GoogleCrawlerTest {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String KEY_STRING = "&key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private final String CRITERIA = "-";

    private final int TOTAL_ITEMS = 100;
    private final int ITEMS_ON_PAGE = 20;

    @Test
    public void testStartMethod(){
        //given
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());
        CrawlerActionListener mockListener = mock(CrawlerActionListener.class);
        GoogleCrawler crawler = new GoogleCrawler(mockExecutorService);

        //when
        crawler.start(mockListener);

        //then
        verify(mockExecutorService, times(1)).execute(any());
    }

    @Test
    public void testGetRequestParameters(){
        //given
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());
        GoogleCrawler crawler = new GoogleCrawler(mockExecutorService);
        SoftAssert softAssert = new SoftAssert();

        //then
        softAssert.assertEquals(crawler.getId(), GoogleCrawler.GOOGLE_CRAWLER_ID);
        softAssert.assertEquals(crawler.getBaseUrl(), BASE_URL);
        softAssert.assertEquals(crawler.getCriteria(), CRITERIA);
    }

    @Test
    public void testSendRestOfResponses(){
        //given
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());

        Paginator mockedPAginator = mock(Paginator.class);
        when(mockedPAginator.getItemsOnPage()).thenReturn(ITEMS_ON_PAGE);
        when(mockedPAginator.getTotalNumberOfItems()).thenReturn(TOTAL_ITEMS);

        CrawlerActionListener actionListener = mock(CrawlerActionListener.class);
        GoogleCrawler crawler = new GoogleCrawler(mockExecutorService);

        //when
        crawler.sendRestOfResponses(mockedPAginator, actionListener);

        //then
        verify(mockExecutorService, times(4)).execute(any());
    }

    @Test
    public void testAddBooksToResultList(){
        //given
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());
        GoogleCrawler crawler = new GoogleCrawler(mockExecutorService);
        BookDiscountData mokedData = mock(BookDiscountData.class);

        List<BookDiscountData> bookDiscountDataList = new LinkedList<>();

        //when
        bookDiscountDataList.add(mokedData);
        crawler.addBooksToResultList(bookDiscountDataList);

        //then
        Assert.assertEquals(crawler.getBooksData().size(), 1);
    }

    @Test
    public void testFinishCrawlerMethod(){
        //given
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        doNothing().when(mockExecutorService).execute(any());
        GoogleCrawler crawler = new GoogleCrawler(mockExecutorService);

        //when
        crawler.finishCrawler(booksData -> {

            //then
            Assert.assertEquals(crawler.getBooksData().size(), 0);
        });
    }

}
