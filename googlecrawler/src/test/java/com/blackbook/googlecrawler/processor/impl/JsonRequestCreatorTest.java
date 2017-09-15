package com.blackbook.googlecrawler.processor.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Siarhei Shauchenka at 15.09.17
 */
public class JsonRequestCreatorTest {

    @Test
    public void testSuccessMakeRequestMethod() throws UnirestException {
        //given
        String fakeUrl = "URL";
        JSONObject fakeResponseObject = new JSONObject();
        JsonNode mockedJsonNode = mock(JsonNode.class);
        when(mockedJsonNode.getObject()).thenReturn(fakeResponseObject);

        HttpResponse<JsonNode> mockedResponse = mock(HttpResponse.class);

        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getBody()).thenReturn(mockedJsonNode);

        JsonRequestCreator jsonRequestCreator = new JsonRequestCreator(fakeUrl);
        jsonRequestCreator.getRequest = mock(GetRequest.class);

        when(jsonRequestCreator.getRequest.asJson()).thenReturn(mockedResponse);

        //when
        jsonRequestCreator.makeRequest();

        //then
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(jsonRequestCreator.isSuccess(), true);
        softAssert.assertEquals(jsonRequestCreator.getResponseBody(), fakeResponseObject);
    }

    @Test
    public void testFailedMakeRequestMethod() throws UnirestException {
        //given
        String fakeUrl = "URL";
        String failedStatusText = "fail";

        HttpResponse<JsonNode> mockedResponse = mock(HttpResponse.class);

        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        when(mockedResponse.getStatusText()).thenReturn(failedStatusText);

        JsonRequestCreator jsonRequestCreator = new JsonRequestCreator(fakeUrl);
        jsonRequestCreator.getRequest = mock(GetRequest.class);

        when(jsonRequestCreator.getRequest.asJson()).thenReturn(mockedResponse);

        //when
        jsonRequestCreator.makeRequest();

        //then
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(jsonRequestCreator.isSuccess(), false);
        softAssert.assertEquals(jsonRequestCreator.getErrorMessage(), failedStatusText);
    }

    @Test
    public void testFailedMakeRequestMethodWithException() throws UnirestException {
        //given
        String fakeUrl = "URL";
        String failedStatusText = "fail";

        HttpResponse<JsonNode> mockedResponse = mock(HttpResponse.class);

        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        when(mockedResponse.getStatusText()).thenReturn(failedStatusText);

        JsonRequestCreator jsonRequestCreator = new JsonRequestCreator(fakeUrl);
        jsonRequestCreator.getRequest = mock(GetRequest.class);

        UnirestException exception = mock(UnirestException.class);
        when(exception.getMessage()).thenReturn(failedStatusText);

        when(jsonRequestCreator.getRequest.asJson()).thenThrow(exception);

        //when
        jsonRequestCreator.makeRequest();

        //then
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(jsonRequestCreator.isSuccess(), false);
        softAssert.assertEquals(jsonRequestCreator.getErrorMessage(), failedStatusText);
    }
}
