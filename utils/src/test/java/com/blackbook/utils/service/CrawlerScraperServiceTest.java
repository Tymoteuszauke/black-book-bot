package com.blackbook.utils.service;

import com.blackbook.utils.callable.SaveBooksCallable;
import com.blackbook.utils.core.ICrawler;
import com.blackbook.utils.view.response.SimpleResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Siarhei Shauchenka at 13.09.17
 */
public class CrawlerScraperServiceTest {

    private SimpleResponse successMockResponse;
    private SimpleResponse failedMockResponse;
    private ScheduledFuture<SimpleResponse> mockSuccessTestFuture, mockFailedTestFuture;

    @BeforeClass
    public void prepareData(){
        successMockResponse = SimpleResponse.builder()
                .code(HttpStatus.SC_OK)
                .message("OK test response")
                .build();

        failedMockResponse = SimpleResponse.builder()
                .code(HttpStatus.SC_NOT_IMPLEMENTED)
                .message("Failed test response")
                .build();

        mockSuccessTestFuture = mock(ScheduledFuture.class);
        mockFailedTestFuture = mock(ScheduledFuture.class);
    }

    @Test
    public void testStart(){
        //given
        ICrawler crawler = mock(ICrawler.class);
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);

        //when
        doNothing().when(crawler).start(any(), any());
        service.saveResultsInDatabase();

        //then
        verify(crawler,times(1)).start(any(), any());
    }

    @Test
    public void testDataSuccessSending() throws ExecutionException, InterruptedException {
        //given
        ICrawler crawler = mock(ICrawler.class);
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);

        ScheduledFuture<SimpleResponse> mockTestFuture = mock(ScheduledFuture.class);


        when(mockTestFuture.get()).thenReturn(successMockResponse);

        when(executorService.submit(any(SaveBooksCallable.class))).thenReturn(mockTestFuture);
        when(executorService.schedule(any(SaveBooksCallable.class), anyLong(), any())).thenReturn(mockTestFuture);

        //when
        service.saveResultsInDatabase();
        service.crawlerConsumer.accept(Collections.EMPTY_LIST);

        //then
        verify(executorService,times(2)).submit(any(SaveBooksCallable.class));
        verify(executorService,times(0)).schedule(any(SaveBooksCallable.class), anyLong(), any());
    }

    @Test
    public void testFirstTimeFailedSending() throws ExecutionException, InterruptedException {
        //given
        ICrawler crawler = mock(ICrawler.class);
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);

        when(mockSuccessTestFuture.get()).thenReturn(successMockResponse);
        when(mockFailedTestFuture.get()).thenReturn(failedMockResponse);

        when(executorService.submit(any(SaveBooksCallable.class))).thenReturn(mockFailedTestFuture);
        when(executorService.schedule(any(SaveBooksCallable.class), anyLong(), any())).thenReturn(mockSuccessTestFuture);

        //when
        service.saveResultsInDatabase();
        service.crawlerConsumer.accept(Collections.EMPTY_LIST);

        //then
        verify(executorService,times(2)).submit(any(SaveBooksCallable.class));
        verify(executorService,times(2)).schedule(any(SaveBooksCallable.class), anyLong(), any());
    }

    @Test
    public void testFailedSending() throws ExecutionException, InterruptedException {
        //given
        ICrawler crawler = mock(ICrawler.class);
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);

        when(mockFailedTestFuture.get()).thenReturn(failedMockResponse);

        when(executorService.submit(any(SaveBooksCallable.class))).thenReturn(mockFailedTestFuture);
        when(executorService.schedule(any(SaveBooksCallable.class), anyLong(), any())).thenReturn(mockFailedTestFuture);

        //when
        service.saveResultsInDatabase();
        service.crawlerConsumer.accept(Collections.EMPTY_LIST);

        //then
        verify(executorService,times(1)).submit(any(SaveBooksCallable.class));
        verify(executorService,times(1)).schedule(any(SaveBooksCallable.class), anyLong(), any());
    }

    @Test
    public void testFailedSendingWithException() throws ExecutionException, InterruptedException {
        //given
        ICrawler crawler = mock(ICrawler.class);
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);
        ScheduledFuture<SimpleResponse> mockFailedTestFutureWithException = mock(ScheduledFuture.class);

        when(mockFailedTestFuture.get()).thenReturn(failedMockResponse);
        when(mockFailedTestFutureWithException.get()).thenThrow(InterruptedException.class);

        when(executorService.submit(any(SaveBooksCallable.class))).thenReturn(mockFailedTestFuture);
        when(executorService.schedule(any(SaveBooksCallable.class), anyLong(), any())).thenReturn(mockFailedTestFutureWithException);

        //when
        service.saveResultsInDatabase();
        service.crawlerConsumer.accept(Collections.EMPTY_LIST);

        //then
        verify(executorService,times(1)).submit(any(SaveBooksCallable.class));
        verify(executorService,times(1)).schedule(any(SaveBooksCallable.class), anyLong(), any());
    }

    @Test
    public void testNormalTerminatingExecutor() throws InterruptedException {
        //given
        ICrawler crawler = mock(ICrawler.class);
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);

        when(executorService.awaitTermination(anyLong(), any(TimeUnit.class))).thenReturn(false);

        //when
        service.terminateExecutor();

        //then
        verify(executorService,times(1)).shutdown();
        verify(executorService,times(1)).awaitTermination(anyLong(),any(TimeUnit.class));
        verify(executorService,times(1)).shutdownNow();
    }

    //This test broke JVM normal execution. The cause needs to be researched.

//    @Test
//    public void testTerminatingExecutorWithException() throws InterruptedException {
//        //given
//        ICrawler crawler = mock(ICrawler.class);
//        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
//        CrawlerScraperService service = new CrawlerScraperService(crawler, executorService);
//
//        when(executorService.awaitTermination(anyLong(), any(TimeUnit.class))).thenThrow(InterruptedException.class);
//
//        //when
//        service.terminateExecutor();
//
//        //then
//        verify(executorService,times(1)).shutdown();
//        verify(executorService,times(1)).awaitTermination(anyLong(),any(TimeUnit.class));
//        verify(executorService,times(1)).shutdownNow();
//    }
}
