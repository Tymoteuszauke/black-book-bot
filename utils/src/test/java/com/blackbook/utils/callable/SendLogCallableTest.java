package com.blackbook.utils.callable;

import com.blackbook.utils.model.creationmodel.BookDiscountData;
import com.blackbook.utils.model.creationmodel.SendLogCallableDataModel;
import com.blackbook.utils.model.log.LogEvent;
import com.blackbook.utils.response.SimpleResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Siarhei Shauchenka at 13.09.17
 */
public class SendLogCallableTest {

    private final String TEST_MESSAGE = "testMessage";
    private final String TEST_API_END_POIND = "fake end point";

    private final int FAKE_CRAWLER_ID = 0;

    @Test
    public void testCreation(){
        //given
        List<BookDiscountData> booksData = Collections.emptyList();
        LogEvent.LogEventBuilder logEventBuilder = mock(LogEvent.LogEventBuilder.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        SendLogCallableDataModel model = SendLogCallableDataModel.builder()
                .booksData(booksData)
                .crawlerId(FAKE_CRAWLER_ID)
                .logEventBuilder(logEventBuilder)
                .persistenceApiEndpoint(TEST_API_END_POIND)
                .restTemplate(restTemplate)
                .build();

        //when
        SendLogCallable sendLogCallable = new SendLogCallable(()-> model);

        //then
        Assert.assertEquals(sendLogCallable.getBooksData(), booksData);
        Assert.assertEquals(sendLogCallable.getPersistenceApiEndpoint(), TEST_API_END_POIND);
        Assert.assertEquals(sendLogCallable.getRestTemplate(), restTemplate);
        Assert.assertEquals(sendLogCallable.getCrawlerId(), FAKE_CRAWLER_ID);
        Assert.assertEquals(sendLogCallable.getLogEventBuilder(), logEventBuilder);

    }

    @Test
    public void testCall(){
        //given
        List<BookDiscountData> booksData = Collections.emptyList();
        LogEvent.LogEventBuilder logEventBuilder = mock(LogEvent.LogEventBuilder.class);
        ResponseEntity<SimpleResponse<String>> responseForMock = ResponseEntity.ok(new SimpleResponse<>(TEST_MESSAGE));
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(ParameterizedTypeReference.class))).thenReturn(responseForMock);

        SendLogCallable sendLogCallable = new SendLogCallable(() -> SendLogCallableDataModel.builder()
                .booksData(booksData)
                .crawlerId(FAKE_CRAWLER_ID)
                .logEventBuilder(logEventBuilder)
                .persistenceApiEndpoint(TEST_API_END_POIND)
                .restTemplate(restTemplate)
                .build());

        //when
        ResponseEntity<SimpleResponse<String>> response = sendLogCallable.call();

        //then
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().getResponse(), TEST_MESSAGE);
    }
}
