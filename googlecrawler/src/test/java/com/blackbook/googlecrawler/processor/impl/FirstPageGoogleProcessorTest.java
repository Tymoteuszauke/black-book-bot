package com.blackbook.googlecrawler.processor.impl;

import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import org.json.JSONObject;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Siarhei Shauchenka at 08.09.17
 */
@Test
public class FirstPageGoogleProcessorTest {

    private final static String TEST_REQUEST = "some request";

    private FirstPageGoogleProcessor processor;
    private CrawlerProcessorListener mockListener;

    @BeforeClass
    public void prepareData(){
        mockListener = Mockito.mock(CrawlerProcessorListener.class);
        doNothing().when(mockListener).success(any());
        doNothing().when(mockListener).failed(any());
        processor = new FirstPageGoogleProcessor(TEST_REQUEST, mockListener);
    }

    public void testSuccessResult(){
        processor.onSuccess(new JSONObject());
        verify(mockListener, times(1)).success(any());
    }

    public void testFailedResult(){
        processor.onFailed("");
        verify(mockListener, times(1)).failed(any());
    }
}
