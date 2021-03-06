package com.blackbook.utils.callable;

import com.blackbook.utils.model.creationmodel.BookDiscountData;
import com.blackbook.utils.model.creationmodel.SaveBooksCallableDataModel;
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
public class SaveBooksCallableTest {

    private final String TEST_MESSAGE = "test message";
    private final String TEST_API_END_POIND = "fake end point";


    @Test
    public void testCreation(){
        //given
        List<BookDiscountData> booksDataMock = Collections.emptyList();
        RestTemplate restTemplate = mock(RestTemplate.class);
        SaveBooksCallableDataModel model = SaveBooksCallableDataModel.builder()
                .booksData(booksDataMock)
                .persistenceApiEndpoint(TEST_API_END_POIND)
                .restTemplate(restTemplate)
                .build();

        //when
        SaveBooksCallable saveBooksCallable = new SaveBooksCallable(()-> model);

        //then
        Assert.assertEquals(saveBooksCallable.getBooksData(), booksDataMock);
        Assert.assertEquals(saveBooksCallable.getPersistenceApiEndpoint(), TEST_API_END_POIND);
        Assert.assertEquals(saveBooksCallable.getRestTemplate(), restTemplate);
    }

    @Test
    public void testCall(){
        //given
        List<BookDiscountData> booksDataMock = Collections.emptyList();
        ResponseEntity<SimpleResponse<String>> responseForMock = ResponseEntity.ok(new SimpleResponse<>(TEST_MESSAGE));
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        when(restTemplateMock.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseForMock);

        SaveBooksCallable saveBooksCallable = new SaveBooksCallable(()-> SaveBooksCallableDataModel.builder()
                .restTemplate(restTemplateMock)
                .persistenceApiEndpoint(TEST_API_END_POIND)
                .booksData(booksDataMock)
                .build());
        //when
        ResponseEntity<SimpleResponse<String>> response = saveBooksCallable.call();

        //then
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().getResponse(), TEST_MESSAGE);
    }

}
