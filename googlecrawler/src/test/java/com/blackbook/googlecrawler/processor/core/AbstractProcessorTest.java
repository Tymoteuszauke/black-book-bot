package com.blackbook.googlecrawler.processor.core;

import com.blackbook.googlecrawler.processor.impl.JsonRequestCreator;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * @author Siarhei Shauchenka at 15.09.17
 */
public class AbstractProcessorTest {

    @Test
    public void testSuccessRunMethod(){
        //given
        JSONObject fakeResponseBody = new JSONObject();
        JsonRequestCreator requestCreator = mock(JsonRequestCreator.class);
        doNothing().when(requestCreator).makeRequest();
        when(requestCreator.isSuccess()).thenReturn(true);
        when(requestCreator.getResponseBody()).thenReturn(fakeResponseBody);



        CrawlerProcessorListener mockedListener = mock(CrawlerProcessorListener.class);
        AbstractProcessor processor = new AbstractProcessor(requestCreator, mockedListener) {
            @Override
            public void onSuccess(JSONObject responseBody) {
                //then
                Assert.assertEquals(responseBody, fakeResponseBody);
            }
        };

        //when
        processor.run();
    }

    @Test
    public void testFailedRunMethod(){
        //given
        String failMessage = "failed";
        JsonRequestCreator requestCreator = mock(JsonRequestCreator.class);
        doNothing().when(requestCreator).makeRequest();
        when(requestCreator.isSuccess()).thenReturn(false);
        when(requestCreator.getErrorMessage()).thenReturn(failMessage);



        CrawlerProcessorListener mockedListener = mock(CrawlerProcessorListener.class);
        AbstractProcessor processor = new AbstractProcessor(requestCreator, mockedListener) {
            @Override
            public void onSuccess(JSONObject responseBody) {
                //here happens nothing
            }

            @Override
            public void onFailed(String errorMessage) {
                //then
                Assert.assertEquals(errorMessage, failMessage);
            }
        };

        //when
        processor.run();
    }
}
